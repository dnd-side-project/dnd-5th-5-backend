package com.meme.ala.domain.member.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.member.model.dto.MemberFriendDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberFriendMapper;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import com.meme.ala.domain.member.service.MemberFriendService;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberFriendsController {
    private final MemberFriendService memberFriendService;
    private final MemberFriendMapper memberFriendMapper;

    @GetMapping("/me/friends")
    public ResponseEntity<ResponseDto<List<MemberFriendDto>>> getMemberFriends(@CurrentUser Member member) {
        List<Member> memberFriendList = memberFriendService.getMemberFriend(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_FRIENDS,
                        memberFriendList.stream().map(memberFriendMapper::toMemberFriendDtoFromMember).collect(Collectors.toList()))
                );
    }
}
