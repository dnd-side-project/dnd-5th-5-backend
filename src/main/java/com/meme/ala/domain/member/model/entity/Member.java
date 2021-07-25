package com.meme.ala.domain.member.model.entity;

import com.meme.ala.core.auth.jwt.Authority;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "MEMBER")
public class Member {
    @Id
    private ObjectId id;

    private String email;

    private String googleId;

    private String naverId;

    private MemberSetting memberSetting;

    @Builder.Default
    private String authority=Authority.ROLE_USER;

    @Builder.Default
    @DBRef
    Map<AlaCard, AlaCardSetting> alaCardAlaCardSettingMap=new HashMap<>();
}
