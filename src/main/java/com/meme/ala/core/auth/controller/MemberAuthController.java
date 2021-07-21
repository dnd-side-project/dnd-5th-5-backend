package com.meme.ala.core.auth.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value="/api/v1/oauth")
@RequiredArgsConstructor
@RestController
public class MemberAuthController {
    private final MemberService memberService;

    @PostMapping("/jwt/{provider}")
    public ResponseEntity<ResponseDto<String>> jwtGoogleCreate(@RequestBody Map<String, Object> data,
                                                               @PathVariable("provider") String provider) {
        Map<String,String> oauthMap= memberService.loginOrJoin(data, provider);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, oauthMap.get("message"), oauthMap.get("jwt")));
    }
}