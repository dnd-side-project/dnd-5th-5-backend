package com.meme.ala.domain.aggregation.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.member.model.entity.Member;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AggregationService {
    public Aggregation findByMember(Member member);

    public void initAggregation(Member member);

    public void save(Aggregation aggregation);

    public void submitWordList(Aggregation aggregation, List<String> wordIdList) throws UnsupportedEncodingException;

    public Integer getUserCount();

    void addAggregation(Member member);
}