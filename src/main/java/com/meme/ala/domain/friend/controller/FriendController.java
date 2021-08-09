package com.meme.ala.domain.friend.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.friend.model.dto.FriendDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.model.mapper.FriendMapper;
import com.meme.ala.domain.friend.service.FriendInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1/friend")
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendInfoService friendInfoService;
    private final FriendMapper friendMapper;

    @GetMapping
    public ResponseEntity<ResponseDto<List<FriendDto>>> getMemberFriends(@CurrentUser Member member) {
        List<Member> memberFriendList = friendInfoService.getMemberFriend(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_FRIENDS,
                        memberFriendList.stream().map(friendMapper::toMemberFriendDtoFromMember).collect(Collectors.toList()))
                );
    }

    @PostMapping("/{nickname}")
    public ResponseEntity<ResponseDto> addMemberFriend(@CurrentUser Member member, @PathVariable String nickname){
        friendInfoService.followingFriend(member, nickname);

        return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS));
    }

    @PostMapping("/accept/{nickname}")
    public ResponseEntity<ResponseDto> acceptMemberFriend(@CurrentUser Member member, @PathVariable String nickname){
        friendInfoService.acceptFollowerToFriend(member, nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS));
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<ResponseDto> deleteMemberFriend(@CurrentUser Member member, @PathVariable String nickname){

        friendInfoService.cutOffFriend(member, nickname);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.of(HttpStatus.NO_CONTENT, ResponseMessage.DELETED));
    }
}
