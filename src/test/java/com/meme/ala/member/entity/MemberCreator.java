package com.meme.ala.member.entity;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import org.bson.types.ObjectId;

public class MemberCreator {
    public static Member testMember=Member.builder()
            .id(new ObjectId("60f3f89c9f21ff292724eb38"))
            .email("test@gmail.com")
            .googleId("12312345648")
            .memberSetting(MemberSetting.builder()
                    .nickname("testNickname")
                    .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                    .isAlarmed(true)
                    .isDeleted(false)
                    .isOpen(true)
                    .statusMessage("너 자신을 ala")
                    .build()
            )
            .build();
}
