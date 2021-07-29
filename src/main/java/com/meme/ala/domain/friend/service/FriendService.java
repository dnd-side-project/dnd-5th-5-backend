package com.meme.ala.domain.friend.service;

import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface FriendService {
    List<Member> getMemberFriend(Member member);
    void addMemberFriend(Member member, String followingNickname);
    void acceptMemberFriend(Member member, String followerNickname);
}
