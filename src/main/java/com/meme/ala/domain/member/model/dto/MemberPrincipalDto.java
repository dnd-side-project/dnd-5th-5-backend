package com.meme.ala.domain.member.model.dto;

import com.meme.ala.core.auth.oauth.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberPrincipalDto {
    private String email;
    private String nickname;
    private String statusMessage;
    private String imgUrl;
    private Boolean isOpen;
    private Provider provider;
}
