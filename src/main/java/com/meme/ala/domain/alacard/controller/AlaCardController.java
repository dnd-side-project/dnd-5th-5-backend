package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.request.SubmitWordDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1/alacard")
@RequiredArgsConstructor
@RestController
public class AlaCardController {
    private final AlaCardService alaCardService;
    private final AggregationService aggregationService;
    private final MemberCardService memberCardService;
    private final MemberService memberService;

    @Value("${alacard.maxwords}")
    private int maxWords;


    @PostMapping
    public ResponseEntity<ResponseDto<AlaCardSaveDto>> alaCardSave(@RequestBody AlaCardSaveDto dto) {
        alaCardService.save(AlaCardSaveMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, dto));
    }

    @GetMapping("/wordlist")
    public ResponseEntity<ResponseDto<List<SelectionWordDto>>> getWordList(@CookieValue(name = "id", required = false) String cookieId,
                                                                           @RequestParam String nickname, @RequestParam long offset, HttpServletResponse response) {

        if (null == cookieId) {
            boolean shuffle = true;

            cookieId = "temp" + LocalDateTime.now(); // TODO: 임의의 id 생성 반드시 고쳐야함. 버그생성됨

            Cookie cookie = new Cookie("id", cookieId);

            cookie.setMaxAge(10 * 60);

            response.addCookie(cookie);

            memberCardService.setTemporalWordList(cookieId, nickname, shuffle);
        }

        List<SelectionWordDto> wordDtoList = memberCardService.getWordList(cookieId).stream().skip(offset).limit(maxWords).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, wordDtoList));
    }

    @PatchMapping("/wordlist")
    public ResponseEntity<ResponseDto<String>> submitWordList(@RequestParam String nickname, @RequestBody SubmitWordDto submitWordDto) {
        Member member = memberService.findByNickname(nickname);
        Aggregation aggregation = aggregationService.findByMember(member);
        aggregationService.submitWordList(member, aggregation, submitWordDto.getWords());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, ResponseMessage.SUBMITTED));
    }

    @GetMapping("/alacardlist")
    public ResponseEntity<ResponseDto<List<AlaCardDto>>> alacardlist(@CurrentUser Member member) {
        List<AlaCardDto> alaCardDtoList = alaCardService.getAlaCardDtoList(member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, alaCardDtoList));
    }

    @GetMapping("/backgrounds")
    public ResponseEntity<ResponseDto<List<String>>> backgrounds() throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, alaCardService.getBackgroundImageUrls()));
    }

    @PostMapping("/alacardsetting")
    public ResponseEntity<ResponseDto> postAlaCardSetting(@RequestBody AlaCardSettingDto alaCardSettingDto) {
        alaCardService.saveSetting(alaCardSettingDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.NO_CONTENT, ResponseMessage.SUCCESS));
    }
}