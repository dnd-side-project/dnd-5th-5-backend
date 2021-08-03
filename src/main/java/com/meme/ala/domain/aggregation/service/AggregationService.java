package com.meme.ala.domain.aggregation.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface AggregationService {
    public Aggregation findByMember(Member member);

    public void initAggregation(Member member);

    public void save(Aggregation aggregation);

    public void submitWordList(Member member, Aggregation aggregation, List<SelectionWordDto> wordDtoList);
}