package com.meme.ala.domain.aggregation.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AggregationService {
    Aggregation findByMember(Member member);

    void initAggregation(Member member, List<AlaCardSettingPair> alaCardSettingPairList);

    void save(Aggregation aggregation);

    void submitWordList(Member member, Aggregation aggregation, List<String> wordIdList) throws UnsupportedEncodingException;

    Integer getUserCount();
}