package com.meme.ala.core.auth.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.core.auth.jwt.JwtProvider;
import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.core.auth.oauth.service.OAuthService;
import com.meme.ala.domain.member.model.dto.JwtVO;
import com.meme.ala.domain.member.service.MemberAuthService;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/api/v1/oauth")
@RequiredArgsConstructor
@RestController
public class MemberAuthController {
    private final MemberService memberService;
    private final MemberAuthService memberAuthService;
    private final OAuthService oAuthService;
    private final JwtProvider jwtProvider;

    @PostMapping("/jwt/{provider}")
    public ResponseEntity<ResponseDto<String>> jwtCreate(@RequestBody Map<String, Object> data,
                                                         @PathVariable("provider") String provider) {

        Map<String, String> oAuthMap = new HashMap<>();

        OAuthUserInfo authUserInfo = oAuthService.getMemberByProvider(data, provider);

        if (!memberService.existsProviderId(authUserInfo.getProviderId())) {
            oAuthMap.put("message", ResponseMessage.JOIN);
            memberService.join(authUserInfo, provider);
        } else
            oAuthMap.put("message", ResponseMessage.LOGIN);

        oAuthMap.put("jwt", jwtProvider.createToken(authUserInfo.getProviderId()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, oAuthMap.get("message"), oAuthMap.get("jwt")));
    }

    @GetMapping("/jwt/naver")
    public ResponseEntity<ResponseDto<String>> jwtNaverCreate(@RequestParam(required = false) String access_token) {
        JwtVO result = memberAuthService.tokenTojwt(access_token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, result.getMessage(), result.getJwt()));
    }
}