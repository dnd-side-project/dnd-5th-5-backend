package com.meme.ala.domain.friend.service;

import com.meme.ala.core.annotation.FlushFriendAlarm;
import com.meme.ala.core.annotation.Notification;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.friend.model.entity.FriendRelation;
import com.meme.ala.domain.friend.repository.FriendInfoRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendInfoServiceImpl implements FriendInfoService {
    private final FriendInfoRepository friendInfoRepository;
    private final MemberRepository memberRepository;
    private final FriendService friendService;
    private final MemberService memberService;

    @Override
    @Transactional
    public void initFriendInfo(Member member) {
        FriendInfo friendInfo = FriendInfo.builder().memberId(member.getId()).build();

        friendInfoRepository.save(friendInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberFriend(Member member) {
        FriendInfo friendInfo = getFriendInfo(member);

        List<Member> friendList = new LinkedList<>();

        for (ObjectId friendId : friendInfo.getFriends()) {
            try {
                Member friend = memberService.findByMemberId(friendId);
                friendList.add(friend);
            } catch (EntityNotFoundException e) {
                continue;
            }
        }

        return friendList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberAllFriendInfo(Member member) {
        FriendInfo friendInfo = getFriendInfo(member);

        List<Member> friendList = new LinkedList<>();

        for (ObjectId friendId : friendInfo.getFriends()) {
            try {
                Member friend = memberService.findByMemberId(friendId);
                friendList.add(friend);
            } catch (EntityNotFoundException e) {
                continue;
            }
        }
        for (ObjectId friendId : friendInfo.getFriendAcceptancePendingList()) {
            try {
                Member friend = memberService.findByMemberId(friendId);
                friendList.add(friend);
            } catch (EntityNotFoundException e) {
                continue;
            }
        }
        for (ObjectId friendId : friendInfo.getMyAcceptancePendingList()) {
            try {
                Member friend = memberService.findByMemberId(friendId);
                friendList.add(friend);
            } catch (EntityNotFoundException e) {
                continue;
            }
        }
        return friendList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberFollower(Member member) {
        FriendInfo friendInfo = getFriendInfo(member);

        List<Member> followerList = new LinkedList<>();

        for (ObjectId followerId : friendInfo.getMyAcceptancePendingList()) {
            try {
                Member friend = memberService.findByMemberId(followerId);
                followerList.add(friend);
            } catch (EntityNotFoundException e) {
                continue;
            }
        }

        return followerList;
    }

    @Override
    public FriendRelation getRelation(Member member, Member person) {
        FriendInfo memberFriendInfo = getFriendInfo(member);

        return memberFriendInfo.getRelation(person.getId());
    }

    @Override
    @Notification(category = AlarmCategory.FRIEND_ALARM)
    @Transactional
    public void followingFriend(Member member, Member following) {
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followingFriendInfo = getFriendInfo(following);

        if (memberFriendInfo.getRelation(following.getId()) != FriendRelation.DEFAULT)
            throw new BusinessException(ErrorCode.NOT_DEFAULT);

        friendService.follow(memberFriendInfo, followingFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(followingFriendInfo, memberFriendInfo));
    }

    @Override
    @Notification(category = AlarmCategory.FRIEND_ALARM)
    @Transactional
    public void acceptFollowerToFriend(Member member, Member follower) {
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followerFriendInfo = getFriendInfo(follower);

        if (memberFriendInfo.getRelation(follower.getId()) != FriendRelation.FOLLOWER)
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        friendService.accept(memberFriendInfo, followerFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    @FlushFriendAlarm
    public void declineFriend(Member member, Member follower) {
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followerFriendInfo = getFriendInfo(follower);

        if (memberFriendInfo.getRelation(follower.getId()) != FriendRelation.FOLLOWER)
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        friendService.decline(memberFriendInfo, followerFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    @FlushFriendAlarm
    public void cancelFollowing(Member member, Member following) {
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followingFriendInfo = getFriendInfo(following);

        if (memberFriendInfo.getRelation(following.getId()) != FriendRelation.FOLLOWING)
            throw new BusinessException(ErrorCode.NOT_FOLLOWING);

        friendService.cancelFollowing(memberFriendInfo, followingFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(followingFriendInfo, memberFriendInfo));
    }

    @Override
    @Transactional
    @FlushFriendAlarm
    public void unFriend(Member member, Member friend) {
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo friendFriendInfo = getFriendInfo(friend);

        if (memberFriendInfo.getRelation(friend.getId()) != FriendRelation.FRIEND)
            throw new BusinessException(ErrorCode.NOT_FRIEND);

        friendService.unFriend(memberFriendInfo, friendFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, friendFriendInfo));
    }

    @Override
    public FriendInfo getFriendInfo(Member member) {
        return friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Transactional
    @Override
    @FlushFriendAlarm
    public void flushFriendInfo(Member targetMember, Member deletedMember) {
        FriendInfo friendInfo = getFriendInfo(targetMember);
        friendInfo
                .getFriends()
                .removeIf(f -> f.equals(deletedMember.getId()));
        friendInfo
                .getFriendAcceptancePendingList()
                .removeIf(f -> f.equals(deletedMember.getId()));
        friendInfo
                .getMyAcceptancePendingList()
                .removeIf(f -> f.equals(deletedMember.getId()));
        friendInfoRepository.save(friendInfo);
    }
}
