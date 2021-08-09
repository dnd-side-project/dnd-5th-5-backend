package com.meme.ala.domain.friend.model.entity;

import lombok.Getter;

@Getter
public enum FriendRelation {
    DEFAULT("일반"),
    FOLLOWING("팔로잉"),
    FOLLOWER("팔로워"),
    FRIEND("친구");

    private final String krRelation;

    private FriendRelation(String krRelation){
        this.krRelation = krRelation;
    }
}
