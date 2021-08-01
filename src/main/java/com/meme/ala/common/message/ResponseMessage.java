package com.meme.ala.common.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseMessage {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String LOGIN = "login";
    public static final String JOIN = "join";
    public static final String UPDATE = "update";
    public static final String DELETED = "deleted";
    public static final String SUBMITTED = "submitted";
    public static final String READ_MEMBER_FRIENDS = "사용자 친구 목록을 조회합니다.";
}
