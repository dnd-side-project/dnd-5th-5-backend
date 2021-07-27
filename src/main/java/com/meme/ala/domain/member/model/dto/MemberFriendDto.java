package com.meme.ala.domain.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberFriendDto {
    private String nickname;
    private String statusMessage;
    private String imgUrl;
}
