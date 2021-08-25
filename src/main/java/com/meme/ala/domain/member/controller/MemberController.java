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
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> myPrincipal(@CurrentUser Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberMapper.toPrincipalDto(member)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> memberPrincipal(@RequestParam String nickname) {
        Member member = memberService.findByNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberMapper.toPrincipalDto(member)));
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

    /**
     * TODO: Member 관련 API 전부 변경이 필요함 -> isActive 체크
     * 1. 친구관계
     * 2. 카드 선택
     * 3. 사용자의 정보를 가져올 때?
     */
    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteMemberByNickname(@CurrentUser Member member) {
        memberService.deleteMember(member);
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