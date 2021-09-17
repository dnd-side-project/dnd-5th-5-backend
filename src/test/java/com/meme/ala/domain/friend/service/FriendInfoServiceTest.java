package com.meme.ala.domain.friend.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.annotation.FlushFriendAlarm;
import com.meme.ala.core.aop.FriendAspect;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.friend.repository.FriendInfoRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class FriendInfoServiceTest {

    @Autowired
    private FriendInfoService friendInfoService;

    @MockBean
    private FriendInfoRepository friendInfoRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Spy
    @InjectMocks
    private FriendAspect friendAspect;

    @Test
    void followFriend() {
        Member member = EntityFactory.testMember(); // objectId : 60f3f89c9f21ff292724eb38

        FriendInfo memberFriendInfo = EntityFactory.testFriendInfo();
        memberFriendInfo.setMemberId(member.getId());
        memberFriendInfo.setFriends(new LinkedList<>());
        memberFriendInfo.setMyAcceptancePendingList(new LinkedList<>());

        Member following = EntityFactory.testMember(); // // objectId : 000000000000000000000001
        following.setId(new ObjectId(EntityFactory.testObjectId() + "1"));
        following.getMemberSetting().setNickname("friendNickname");

        FriendInfo followingFriendInfo = EntityFactory.testFriendInfo();

        given(memberRepository.findByMemberSettingNicknameAndMemberSetting_IsDeleted(eq(following.getMemberSetting().getNickname()), eq(false))).willReturn(Optional.of(following));
        given(friendInfoRepository.findById(eq(following.getId()))).willReturn(Optional.of(followingFriendInfo));
        given(friendInfoRepository.findById(eq(member.getId()))).willReturn(Optional.of(memberFriendInfo));

        friendInfoService.followingFriend(member, following);

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

        given(memberRepository.findByMemberSettingNicknameAndMemberSetting_IsDeleted(eq(follower.getMemberSetting().getNickname()), eq(false))).willReturn(Optional.of(follower));
        given(friendInfoRepository.findById(eq(member.getId()))).willReturn(Optional.of(memberFriendInfo));
        given(friendInfoRepository.findById(eq(follower.getId()))).willReturn(Optional.of(followerFriendInfo));
        doNothing().when(friendAspect).afterReturning(any(JoinPoint.class), any(FlushFriendAlarm.class));

        friendInfoService.acceptFollowerToFriend(member, follower);

        assertEquals(memberFriendInfo.getMyAcceptancePendingList().size(), 2);
        assertEquals(memberFriendInfo.getFriends().size(), 3);
        assertEquals(memberFriendInfo.getFriends().get(2), follower.getId());

        assertEquals(followerFriendInfo.getFriends().get(0), member.getId());
    }
}