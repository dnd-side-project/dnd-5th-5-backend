package com.meme.ala.domain.member.service;

import com.meme.ala.domain.member.model.entity.Member;

public interface MemberCardService {
    public void assignCard(Member member, int num);
}