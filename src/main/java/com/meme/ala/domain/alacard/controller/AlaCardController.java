package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.request.SubmitWordDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/api/v1/alacard")
@RequiredArgsConstructor
@RestController
public class AlaCardController {
    private final AlaCardService alaCardService;
    private final AggregationService aggregationService;

    @PostMapping
    public ResponseEntity<ResponseDto<AlaCardSaveDto>> alaCardSave(@RequestBody AlaCardSaveDto dto) {
        alaCardService.save(AlaCardSaveMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, dto));
    }

    @GetMapping("/wordlist")
    public ResponseEntity<ResponseDto<List<SelectionWordDto>>> getWordList(@CurrentUser Member member, @RequestParam String nickname) {
        List<SelectionWordDto> wordDtoList = alaCardService.getWordList(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, wordDtoList));
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseDto<?>> test(@CookieValue(name = "id", required = false) String cookieId,
                                               @RequestParam String nickname, HttpServletResponse response){

        if(null == cookieId){
            boolean shuffle = true;

            cookieId = "temp" + LocalDate.now(); // TODO: 임의의 id 생성

            Cookie cookie = new Cookie("id", cookieId);

            cookie.setMaxAge(10 * 60);

            alaCardService.setTemporalWordList(cookieId, nickname, shuffle);
        }

        List<SelectionWordDto> wordDtoList = alaCardService.getWordList(cookieId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, wordDtoList)); // wordDto List 추가
    }

    @PostMapping("/wordlist")
    public ResponseEntity<ResponseDto<String>> submitWordList(@CurrentUser Member member, @RequestBody SubmitWordDto submitWordDto) {
        Aggregation aggregation = aggregationService.findByMember(member);
        alaCardService.submitWordList(member, aggregation, submitWordDto.getWords());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, ResponseMessage.SUBMITTED));
    }

    @GetMapping("/alacardlist")
    public ResponseEntity<ResponseDto<List<AlaCardDto>>> alacardlist(@CurrentUser Member member) {
        List<AlaCardDto> alaCardDtoList = alaCardService.getAlaCardDtoList(member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, alaCardDtoList));
    }
}