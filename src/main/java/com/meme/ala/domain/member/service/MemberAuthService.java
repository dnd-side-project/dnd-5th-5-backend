package com.meme.ala.domain.member.service;

import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.common.utils.NaverOauthUtil;
import com.meme.ala.core.auth.jwt.JwtProvider;
import com.meme.ala.core.auth.oauth.model.OAuthProvider;
import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.domain.member.model.dto.JwtVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberAuthService {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    public JwtVO tokenTojwt(String accessToken) {
        OAuthUserInfo authUserInfo = NaverOauthUtil.naverTokenToNaverUser(accessToken);
        String message;
        if (!memberService.existsEmail(authUserInfo.getEmail())) {
            message = ResponseMessage.JOIN;
            memberService.join(authUserInfo, OAuthProvider.NAVER);
        } else
            message = ResponseMessage.LOGIN;
        String jwt = jwtProvider.createToken(authUserInfo.getEmail());
        return new JwtVO(message, jwt);
    }
}
