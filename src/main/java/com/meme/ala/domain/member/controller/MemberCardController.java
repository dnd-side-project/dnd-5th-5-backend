package com.meme.ala.domain.member.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberCardController {
    private final MemberCardService memberCardService;
    private final AggregationService aggregationService;

    @PatchMapping("/acquire")
    public ResponseEntity<ResponseDto<?>> updateMemberCard(@CurrentUser Member member){
        memberCardService.assignCard(member, 1);
        aggregationService.addAggregation(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, member.getAlaCardSettingPairList().size()));
    }
}
