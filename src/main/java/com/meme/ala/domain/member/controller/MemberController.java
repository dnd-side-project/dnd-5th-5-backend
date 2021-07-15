package com.meme.ala.domain.member.controller;

import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.core.auth.oauth.GoogleUser;
import com.meme.ala.core.auth.oauth.OAuthUserInfo;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    // TODO: 2021.7.15. 성공 시 JWT 토큰 생성 및 반환하는 기능 추가 - jongmin
    @PostMapping("/oauth/jwt/google")
    public ResponseEntity<ResponseDto<String>> jwtCreate(@RequestBody Map<String, Object> data) {
        OAuthUserInfo googleUser =
                new GoogleUser((Map<String, Object>)data.get("profileObj"));
        Optional<Member> optionalMember =
                memberRepository.findByEmail(googleUser.getEmail());
        if(optionalMember.isPresent()){
            return new ResponseEntity<ResponseDto<String>>(
                    new ResponseDto<String>(HttpStatus.OK.value(),"success","auth success")
                    ,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<ResponseDto<String>>(
                    new ResponseDto<String>(HttpStatus.NOT_FOUND.value(),"not found",null)
                    ,HttpStatus.NOT_FOUND);
        }
    }
}
