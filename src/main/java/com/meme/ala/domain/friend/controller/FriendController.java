package com.meme.ala.domain.friend.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.friend.model.dto.FriendDto;
import com.meme.ala.domain.friend.model.dto.FriendRelationDto;
import com.meme.ala.domain.friend.model.entity.FriendRelation;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.model.mapper.FriendMapper;
import com.meme.ala.domain.friend.service.FriendInfoService;
import com.meme.ala.domain.member.service.MemberService;
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
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<FriendDto>>> getMemberFriends(@CurrentUser Member member) {
        List<Member> memberFriendList = friendInfoService.getMemberFriend(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_FRIENDS,
                        memberFriendList.stream().map(friendMapper::toMemberFriendDtoFromMember).collect(Collectors.toList()))
                );
    }

    @GetMapping("/info/followers")
    public ResponseEntity<ResponseDto<List<FriendDto>>> getMemberFollowers(@CurrentUser Member member) {
        List<Member> memberFollowerList = friendInfoService.getMemberFollower(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_FOLLOWERS,
                        memberFollowerList.stream().map(friendMapper::toMemberFriendDtoFromMember).collect(Collectors.toList()))
                );
    }

    @GetMapping("/relation/{nickname}")
    public ResponseEntity<ResponseDto<FriendRelationDto>> getRelationWithFriend(@CurrentUser Member member, @PathVariable String nickname){
        Member person =  memberService.findByNickname(nickname);
        FriendRelation relation = friendInfoService.getRelation(member, person);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_MEMBER_AND_PERSON_RELATION, new FriendRelationDto(nickname, relation.getKrRelation())));
    }

    @PatchMapping("/{nickname}")
    public ResponseEntity<ResponseDto> following(@CurrentUser Member member, @PathVariable String nickname){
        Member following = memberService.findByNickname(nickname);
        friendInfoService.followingFriend(member, following);

        return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.FOLLOWED));
    }

    @PatchMapping("/accept/{nickname}")
    public ResponseEntity<ResponseDto> acceptMemberFriend(@CurrentUser Member member, @PathVariable String nickname){
        Member follower = memberService.findByNickname(nickname);
        friendInfoService.acceptFollowerToFriend(member, follower);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.ACCEPTED));
    }

    @PatchMapping("/decline/{nickname}")
    public ResponseEntity<ResponseDto> decline(@CurrentUser Member member, @PathVariable String nickname){
        Member following = memberService.findByNickname(nickname);
        friendInfoService.declineFriend(member, following);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.DECLINED));
    }

    @PatchMapping("/cancel/{nickname}")
    public ResponseEntity<ResponseDto> cancelFollowing(@CurrentUser Member member, @PathVariable String nickname){
        Member following = memberService.findByNickname(nickname);
        friendInfoService.cancelFollowing(member, following);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.CANCELED));
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<ResponseDto> deleteMemberFriend(@CurrentUser Member member, @PathVariable String nickname){
        Member friend = memberService.findByNickname(nickname);

        friendInfoService.unFriend(member, friend);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.of(HttpStatus.NO_CONTENT, ResponseMessage.DELETED));
    }
}
