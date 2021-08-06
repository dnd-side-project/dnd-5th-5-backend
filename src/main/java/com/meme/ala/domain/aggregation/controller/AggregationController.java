package com.meme.ala.domain.aggregation.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.aggregation.service.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/aggregation")
@RequiredArgsConstructor
@RestController
public class AggregationController {
    private final AggregationService aggregationService;

    @GetMapping("/usercount")
    public ResponseEntity<ResponseDto<Integer>> getUserCount() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, aggregationService.getUserCount()));
    }
}