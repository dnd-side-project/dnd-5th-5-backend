package com.meme.ala.domain.friend.service;

import com.meme.ala.domain.friend.model.entity.FriendRelation;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface FriendInfoService {
    List<Member> getMemberFriend(Member member);
    void followingFriend(Member member, Member following);
    void acceptFollowerToFriend(Member member, Member follower);
    void declineFriend(Member member, Member follower);
    void cancelFollowing(Member member, Member following);
    void unFriend(Member member, Member friend);
    void initFriendInfo(Member member);
    FriendRelation getRelation(Member member, Member person);
}
