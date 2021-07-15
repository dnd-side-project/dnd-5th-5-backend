package com.meme.ala.domain.member.service;

import java.util.Map;

public interface MemberService {
    public String loginOrJoin(Map<String, Object> data, String provider);
}
