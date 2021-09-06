package com.meme.ala.domain.event.model.entity;

import com.meme.ala.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendEvent {
    private Member member1;
    private Member member2;
}

