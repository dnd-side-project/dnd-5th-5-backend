package com.meme.ala.domain.aggregation.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.member.model.entity.Member;

public interface AggregationService {
    public Aggregation findByMember(Member member);

    public void initAggregation(Member member);
}