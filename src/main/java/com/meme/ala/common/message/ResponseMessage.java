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

    // friend
    public static final String READ_MEMBER_FRIENDS = "사용자 친구 목록을 조회합니다.";
    public static final String READ_MEMBER_AND_PERSON_RELATION = "사용자와 상대방의 관계를 조회합니다.";
    public static final String CANCELED = "canceled";
    public static final String DECLINED = "declined";
    public static final String ACCEPTED = "accepted";
    public static final String FOLLOWED = "followed";
}
