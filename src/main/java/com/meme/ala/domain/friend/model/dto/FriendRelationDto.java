package com.meme.ala.domain.friend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FriendRelationDto {
    private String nickname;
    private String relation;
}
