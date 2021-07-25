package com.meme.ala.domain.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberSetting {
    private String nickname;

    @Builder.Default
    private String statusMessage = "너 자신을 '알라'";

    //TODO: 2021.07.16 이미지 URL 나오면 기본값 세팅
    private String imgUrl;

    @Builder.Default
    private Boolean isAlarmed = true;

    @Builder.Default
    private Boolean isOpen = true;

    @Builder.Default
    private Boolean isDeleted = true;
}
