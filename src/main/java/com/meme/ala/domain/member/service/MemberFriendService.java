package com.meme.ala.domain.member.service;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberFriend;

import java.util.List;

public interface MemberFriendService {
    List<Member> getMemberFriend(Member member);
}
