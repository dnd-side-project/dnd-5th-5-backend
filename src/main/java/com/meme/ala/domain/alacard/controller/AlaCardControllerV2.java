package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.common.utils.CookieGenerator;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.member.service.MemberCardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v2/alacard")
@RequiredArgsConstructor
@RestController
public class AlaCardControllerV2 {
    private final MemberCardService memberCardService;
    private final CookieGenerator cookieGenerator;

    @Value("${alacard.maxwords}")
    private int maxWords;

    @GetMapping("/wordlist")
    public ResponseEntity<ResponseDto<WordListResponse>> getWordList(@RequestParam(required = false) String cookieId,
                                                                     @RequestParam String nickname, @RequestParam long offset) {

        if (null == cookieId) {
            boolean shuffle = true;

            cookieId = nickname + cookieGenerator.randomString();

            memberCardService.setTemporalWordList(cookieId, nickname, shuffle);
        }

        List<SelectionWordDto> wordDtoList = memberCardService.getWordList(cookieId).stream().skip(offset).limit(maxWords).collect(Collectors.toList());

        WordListResponse wordListResponse = new WordListResponse(cookieId, wordDtoList);


        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, wordListResponse));
    }

    @Getter
    @AllArgsConstructor
    public static class WordListResponse {
        private String cookieId;
        private List<SelectionWordDto> wordList;
    }

}
