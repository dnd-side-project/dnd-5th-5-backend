package com.meme.ala.domain.friend.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.friend.model.dto.FriendDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.model.mapper.FriendMapper;
import com.meme.ala.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1/friend")
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService memberFriendService;
    private final FriendMapper memberFriendMapper;

    @GetMapping
    public ResponseEntity<ResponseDto<List<FriendDto>>> getMemberFriends(@CurrentUser Member member) {
        List<Member> memberFriendList = memberFriendService.getMemberFriend(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_FRIENDS,
                        memberFriendList.stream().map(memberFriendMapper::toMemberFriendDtoFromMember).collect(Collectors.toList()))
                );
    }
}
