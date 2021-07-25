package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.service.AlaCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/alacard")
@RequiredArgsConstructor
@RestController
public class AlaCardController {
    private final AlaCardService alaCardService;

    @PostMapping
    public ResponseEntity<ResponseDto<AlaCardSaveDto>> alaCardSave(@RequestBody AlaCardSaveDto dto) {
        alaCardService.save(AlaCardSaveMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, dto));
    }

    @GetMapping("/wordlist")
    public ResponseEntity<ResponseDto<List<SelectionWordDto>>> wordList(@RequestParam String nickname) {
        List<SelectionWordDto> wordDtoList = alaCardService.getWordList(nickname, true);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, wordDtoList));
    }
}