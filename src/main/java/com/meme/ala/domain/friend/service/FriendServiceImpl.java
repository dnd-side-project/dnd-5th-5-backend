package com.meme.ala.domain.friend.service;

import com.meme.ala.domain.friend.model.entity.FriendInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService{
    @Override
    public void follow(FriendInfo a, FriendInfo b) {
        b.getMyAcceptancePendingList().add(a.getMemberId());
        a.getFriendAcceptancePendingList().add(b.getMemberId());
    }

    @Override
    public void accept(FriendInfo a, FriendInfo b) {
        a.getMyAcceptancePendingList().remove(b.getMemberId());
        b.getFriendAcceptancePendingList().remove(a.getMemberId());

        a.getFriends().add(b.getMemberId());
        b.getFriends().add(a.getMemberId());
    }

    @Override
    public void decline(FriendInfo a, FriendInfo b) {
        a.getMyAcceptancePendingList().remove(b.getMemberId());
        b.getFriendAcceptancePendingList().remove(a.getMemberId());
    }

    @Override
    public void cancelFollowing(FriendInfo a, FriendInfo b) {
        b.getMyAcceptancePendingList().remove(a.getMemberId());
        a.getFriendAcceptancePendingList().remove(b.getMemberId());
    }

    @Override
    public void unFriend(FriendInfo a, FriendInfo b) {
        a.getFriends().remove(b.getMemberId());
        b.getFriends().remove(a.getMemberId());
    }
}
