package com.meme.ala.domain.friend.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @InjectMocks
    private FriendServiceImpl friendService;

    @Mock
    private FriendInfoRepository friendInfoRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void addMemberFriend() {
        Member member = EntityFactory.testMember();
        Member following = EntityFactory.testMember();
        following.setId(new ObjectId(EntityFactory.testObjectId() + "1"));
        following.getMemberSetting().setNickname("friendNickname");
        FriendInfo followingFriendInfo = EntityFactory.testFriendInfo();


        given(memberRepository.findByMemberSettingNickname(eq(following.getMemberSetting().getNickname()))).willReturn(Optional.of(following));
        given(friendInfoRepository.findById(eq(following.getId()))).willReturn(Optional.of(followingFriendInfo));

        friendService.addMemberFriend(member, "friendNickname");

        assertEquals(followingFriendInfo.getPendings().size(), 3);
        assertEquals(followingFriendInfo.getPendings().get(2), member.getId());
    }
    /**
     *         Member following = memberRepository.findByMemberSettingNickname(followingNickname)
     *                 .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
     *
     *         FriendInfo followingFriendInfo = friendInfoRepository.findById(following.getId())
     *                 .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
     *
     *         followingFriendInfo.getPendings().add(member.getId());
     *
     *         friendInfoRepository.save(followingFriendInfo);
     */
}