package com.meme.ala.domain.friend.service;

import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface FriendInfoService {
    List<Member> getMemberFriend(Member member);
    void followingFriend(Member member, String followingNickname);
    void acceptFollowerToFriend(Member member, String followerNickname);
    void cutOffFriend(Member member, String friendNickname);
    void initFriendInfo(Member member);
}
