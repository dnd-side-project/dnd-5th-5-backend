package com.meme.ala.domain.member.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.core.auth.oauth.OAuthProvider;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.member.service.MemberService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // TODO: 2021.7.15. 성공 시 JWT 토큰 생성 및 반환하는 기능 추가 - jongmin
    @PostMapping("/api/v1/oauth/jwt/google")
    public ResponseEntity<ResponseDto<String>> jwtGoogleCreate(@RequestBody Map<String, Object> data) {
        String jwtToken = memberService.loginOrJoin(data, OAuthProvider.GOOGLE);
        return new ResponseEntity<ResponseDto<String>>(
                new ResponseDto<String>(HttpStatus.OK.value(), ResponseMessage.SUCCESS, jwtToken)
                , HttpStatus.OK);
    }

    // TODO: 2021.7.16. 성공 시 JWT 토큰 생성 및 반환하는 기능 추가 - jongmin
    @PostMapping("/api/v1/oauth/jwt/naver")
    public ResponseEntity<ResponseDto<String>> jwtNaverCreate(@RequestBody Map<String, Object> data) {
        String jwtToken = memberService.loginOrJoin(data, OAuthProvider.NAVER);
        return new ResponseEntity<ResponseDto<String>>(
                new ResponseDto<String>(HttpStatus.OK.value(), ResponseMessage.SUCCESS, jwtToken)
                , HttpStatus.OK);
    }

    @PostMapping("/api/v1/test")
    public ResponseEntity<ResponseDto<Boolean>> mongotest(){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.SUCCESS, memberService.test()));
    }
}
