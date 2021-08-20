package com.meme.ala.domain.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtVO {
    private String message;
    private String jwt;
}
