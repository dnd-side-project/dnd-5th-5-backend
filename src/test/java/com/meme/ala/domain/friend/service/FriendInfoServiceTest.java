package com.meme.ala.domain.friend.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.friend.repository.FriendInfoRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class FriendInfoServiceTest {

    @InjectMocks
    private FriendInfoServiceImpl friendService;

    @Mock
    private FriendInfoRepository friendInfoRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void addMemberFriend() {
        Member member = EntityFactory.testMember(); // objectId : 60f3f89c9f21ff292724eb38

        FriendInfo memberFriendInfo = EntityFactory.testFriendInfo();
        memberFriendInfo.setMemberId(member.getId());
        memberFriendInfo.setFriends(new LinkedList<>());
        memberFriendInfo.setMyAcceptancePendingList(new LinkedList<>());

        Member following = EntityFactory.testMember(); // // objectId : 000000000000000000000001
        following.setId(new ObjectId(EntityFactory.testObjectId() + "1"));
        following.getMemberSetting().setNickname("friendNickname");

        FriendInfo followingFriendInfo = EntityFactory.testFriendInfo();

        given(memberRepository.findByMemberSettingNickname(eq(following.getMemberSetting().getNickname()))).willReturn(Optional.of(following));
        given(friendInfoRepository.findById(eq(following.getId()))).willReturn(Optional.of(followingFriendInfo));
        given(friendInfoRepository.findById(eq(member.getId()))).willReturn(Optional.of(memberFriendInfo));

        friendService.followingFriend(member, "friendNickname");

        assertEquals(followingFriendInfo.getMyAcceptancePendingList().size(), 3);
        assertEquals(followingFriendInfo.getMyAcceptancePendingList().get(2), member.getId());
    }

    @Test
    void acceptMemberFriend() {
        Member member = EntityFactory.testMember(); // objectId : 000000000000000000000001
        member.setId(new ObjectId(EntityFactory.testObjectId() + "1"));
        member.getMemberSetting().setNickname("friendNickname");

        Member follower = EntityFactory.testMember(); // objectId : 60f3f89c9f21ff292724eb38

        FriendInfo memberFriendInfo = EntityFactory.testFriendInfo();
        memberFriendInfo.getMyAcceptancePendingList().add(follower.getId());

        FriendInfo followerFriendInfo = EntityFactory.testFriendInfo();
        followerFriendInfo.setMemberId(follower.getId());
        followerFriendInfo.setFriends(new LinkedList<>());
        followerFriendInfo.setMyAcceptancePendingList(new LinkedList<>());

        given(memberRepository.findByMemberSettingNickname(eq(follower.getMemberSetting().getNickname()))).willReturn(Optional.of(follower));
        given(friendInfoRepository.findById(eq(member.getId()))).willReturn(Optional.of(memberFriendInfo));
        given(friendInfoRepository.findById(eq(follower.getId()))).willReturn(Optional.of(followerFriendInfo));

        friendService.acceptFollowerToFriend(member, "testNickname");

        assertEquals(memberFriendInfo.getMyAcceptancePendingList().size(), 2);
        assertEquals(memberFriendInfo.getFriends().size(), 3);
        assertEquals(memberFriendInfo.getFriends().get(2), follower.getId());

        assertEquals(followerFriendInfo.getFriends().get(0), member.getId());
    }
}