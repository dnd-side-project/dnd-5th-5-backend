package com.meme.ala.domain.friend.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.repository.FriendInfoRepository;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    private final FriendInfoRepository friendInfoRepository;
    private final MemberRepository memberRepository;

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
    public void addMemberFriend(Member member, String followingNickname){
        Member following = memberRepository.findByMemberSettingNickname(followingNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo followingFriendInfo = friendInfoRepository.findById(following.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(isFriend(member, following))
            throw new BusinessException(ErrorCode.ALREADY_FRIEND);

        followingFriendInfo.getPendings().add(member.getId());

        friendInfoRepository.save(followingFriendInfo);
    }

    @Override
    @Transactional
    public void acceptMemberFriend(Member member, String followerNickname){
        Member follower = memberRepository.findByMemberSettingNickname(followerNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo followerFriendInfo = friendInfoRepository.findById(follower.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(!memberFriendInfo.getPendings().contains(follower.getId()))
            throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);

        memberFriendInfo.getPendings().remove(follower.getId());

        memberFriendInfo.getFriends().add(follower.getId());

        followerFriendInfo.getFriends().add(member.getId());

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, followerFriendInfo));
    }

    @Override
    @Transactional
    public void deleteMemberFriend(Member member, String friendNickname){
        Member friend = memberRepository.findByMemberSettingNickname(friendNickname)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FriendInfo friendFriendInfo = friendInfoRepository.findById(friend.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(!isFriend(member, friend))
            throw new BusinessException(ErrorCode.NOT_FRIEND);

        memberFriendInfo.getFriends().remove(friend.getId());

        friendFriendInfo.getFriends().remove(member.getId());

        friendInfoRepository.saveAll(Arrays.asList(memberFriendInfo, friendFriendInfo));
    }

    private boolean isFriend(Member member, Member friend){
        FriendInfo memberFriendInfo = friendInfoRepository.findById(member.getId())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return memberFriendInfo.getFriends().contains(friend.getId());
    }

}
