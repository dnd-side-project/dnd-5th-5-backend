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

@RequestMapping(value="/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> memberPrincipal(@CurrentUser Member member){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, memberMapper.toPrincipalDto(member)));
    }

    @PutMapping("/me")
    public ResponseEntity<ResponseDto<MemberPrincipalDto>> updateMember(@CurrentUser Member member, @RequestBody MemberPrincipalDto memberPrincipalDto){
        memberService.updateMember(member, memberPrincipalDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.UPDATE, memberMapper.toPrincipalDto(member)));
    }
}