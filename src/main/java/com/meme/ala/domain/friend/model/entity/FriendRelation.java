package com.meme.ala.domain.friend.model.entity;

import lombok.AllArgsConstructor;

public enum FriendRelation {
    DEFAULT("일반"),
    FOLLOWING("팔로잉"),
    FOLLOWER("팔로워"),
    FRIEND("친구");

    private FriendRelation(String relation){
    }

}
