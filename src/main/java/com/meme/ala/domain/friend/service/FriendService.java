package com.meme.ala.domain.friend.service;

import com.meme.ala.domain.friend.model.entity.FriendInfo;

public interface FriendService {
    void follow(FriendInfo a, FriendInfo b); // a가 b를 팔로우하다
    void accept(FriendInfo a, FriendInfo b); // a가 b를 수락하다
    void decline(FriendInfo a, FriendInfo b); // a가 b를 거절하다
    void cancelFollowing(FriendInfo a, FriendInfo b); // a가 b에게 친구 신청한 것을 취소하다
    void unFriend(FriendInfo a, FriendInfo b); // a와 b가 친구사이를 끊다.
}
