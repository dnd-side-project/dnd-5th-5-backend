package com.meme.ala.domain.member.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> memberPrincipal(@CurrentUser Member member) {
        MemberPrincipalDto memberPrincipalDto = memberMapper.toPrincipalDto(member);
        System.out.println(memberPrincipalDto.getEmail());
        System.out.println(memberPrincipalDto.getImgUrl());
        System.out.println(memberPrincipalDto.getNickname());
        System.out.println(memberPrincipalDto.getIsOpen());
        System.out.println(memberPrincipalDto.getStatusMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberPrincipalDto));
    }

    @PatchMapping("/me")
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> updateMember(@CurrentUser Member member, @RequestBody MemberPrincipalDto memberPrincipalDto) {
        memberService.updateMember(member, memberPrincipalDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.UPDATE, memberMapper.toPrincipalDto(member)));
    }

    @GetMapping("/exists")
    public ResponseEntity<ResponseDto<Boolean>> existsNickname(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberService.existsNickname(nickname)));
    }

    @GetMapping("/delete")
    public ResponseEntity<ResponseDto<String>> deleteMemberByNickname(@RequestParam String nickname) {
        memberService.deleteMemberByNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, ResponseMessage.DELETED));
    }

    @GetMapping("/sharelink")
    public ResponseEntity<ResponseDto<String>> getShareSelectLink(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberService.shareSelectLink(nickname)));
    }

    @GetMapping("/mypagelink")
    public ResponseEntity<ResponseDto<String>> getMypageLink(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberService.shareMyPageLink(nickname)));
    }
}