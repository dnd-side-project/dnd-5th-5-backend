package com.meme.ala.domain.friend.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.friend.service.FriendService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.model.entity.Friend;
import com.meme.ala.domain.friend.repository.FriendRepository;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    private FriendRepository memberFriendRepository;
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberFriend(Member member) {
        Friend memberFriend = memberFriendRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return memberFriend
                .getFriends()
                .stream()
                .map((id) -> memberRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)))
                .collect(Collectors.toList());
    }
}
