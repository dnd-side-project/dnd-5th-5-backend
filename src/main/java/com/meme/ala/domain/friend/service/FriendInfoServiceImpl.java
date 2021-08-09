package com.meme.ala.domain.friend.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.friend.model.entity.FriendRelation;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.repository.FriendInfoRepository;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendInfoServiceImpl implements FriendInfoService { // TODO: 각 메서드의 3줄이 비슷한데 개선할 수 있나?
    private final FriendInfoRepository friendInfoRepository;
    private final MemberRepository memberRepository;
    private final FriendService friendService;

    @Override
    @Transactional
    public void initFriendInfo(Member member){
        FriendInfo friendInfo = FriendInfo.builder().memberId(member.getId()).build();

        friendInfoRepository.save(friendInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberFriend(Member member) {
        FriendInfo friendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return friendInfo
                .getFriends()
                .stream()
                .map((id) -> memberRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void followingFriend(Member member, String followingNickname){
        Member following = memberRepository.findByMemberSettingNickname(followingNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo followingFriendInfo = friendInfoRepository.findById(following.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(memberFriendInfo.getRelation(following.getId()) != FriendRelation.DEFAULT)
            throw new BusinessException(ErrorCode.NOT_DEFAULT);

        friendService.follow(memberFriendInfo, followingFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(followingFriendInfo, memberFriendInfo));
    }

    @Override
    @Transactional
    public void acceptFollowerToFriend(Member member, String followerNickname){
        Member follower = memberRepository.findByMemberSettingNickname(followerNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo followerFriendInfo = friendInfoRepository.findById(follower.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(memberFriendInfo.getRelation(follower.getId()) != FriendRelation.FOLLOWER)
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        friendService.accept(memberFriendInfo, followerFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    public void cutOffFriend(Member member, String friendNickname){
        Member friend = memberRepository.findByMemberSettingNickname(friendNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo friendFriendInfo = friendInfoRepository.findById(friend.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(memberFriendInfo.getRelation(friend.getId()) != FriendRelation.FRIEND)
            throw new BusinessException(ErrorCode.NOT_FRIEND);

        friendService.cutOff(memberFriendInfo, friendFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, friendFriendInfo));
    }

}
