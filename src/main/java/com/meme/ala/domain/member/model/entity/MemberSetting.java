package com.meme.ala.domain.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberSetting {
    @Indexed(unique = true)
    private String nickname;

    @Builder.Default
    private String statusMessage = "너 자신을 '알라'";

    @Builder.Default
    private String imgUrl = "https://meme-ala-background.s3.ap-northeast-2.amazonaws.com/img/defaultprofile.svg";

    @Builder.Default
    private Boolean isAlarmed = true;

    @Builder.Default
    private Boolean isOpen = true;

    @Builder.Default
    private Boolean isDeleted = false;
}
