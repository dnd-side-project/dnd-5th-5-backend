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
public class FriendInfoServiceImpl implements FriendInfoService {
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
    public FriendRelation getRelation(Member member, Member person) {
        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return memberFriendInfo.getRelation(person.getId());
    }

    @Override
    @Transactional
    public void followingFriend(Member member, Member following){
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followingFriendInfo = getFriendInfo(following);

        if(memberFriendInfo.getRelation(following.getId()) != FriendRelation.DEFAULT)
            throw new BusinessException(ErrorCode.NOT_DEFAULT);

        friendService.follow(memberFriendInfo, followingFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(followingFriendInfo, memberFriendInfo));
    }

    @Override
    @Transactional
    public void acceptFollowerToFriend(Member member, Member follower){
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followerFriendInfo = getFriendInfo(follower);

        if(memberFriendInfo.getRelation(follower.getId()) != FriendRelation.FOLLOWER)
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        friendService.accept(memberFriendInfo, followerFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    public void declineFriend(Member member, Member follower){
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followerFriendInfo = getFriendInfo(follower);

        if(memberFriendInfo.getRelation(follower.getId()) != FriendRelation.FOLLOWER)
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        friendService.decline(memberFriendInfo, followerFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    public void cancelFollowing(Member member, Member following){
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo followingFriendInfo = getFriendInfo(following);

        if(memberFriendInfo.getRelation(following.getId()) != FriendRelation.FOLLOWING)
            throw new BusinessException(ErrorCode.NOT_FOLLOWING);

        friendService.cancelFollowing(memberFriendInfo, followingFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(followingFriendInfo, memberFriendInfo));
    }

    @Override
    @Transactional
    public void unFriend(Member member, Member friend){
        FriendInfo memberFriendInfo = getFriendInfo(member);
        FriendInfo friendFriendInfo = getFriendInfo(friend);

        if(memberFriendInfo.getRelation(friend.getId()) != FriendRelation.FRIEND)
            throw new BusinessException(ErrorCode.NOT_FRIEND);

        friendService.unFriend(memberFriendInfo, friendFriendInfo);

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, friendFriendInfo));
    }

    private FriendInfo getFriendInfo(Member member){
        return friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

}
