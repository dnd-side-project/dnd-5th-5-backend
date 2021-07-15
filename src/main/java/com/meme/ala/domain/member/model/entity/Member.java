package com.meme.ala.domain.member.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "member")
public class Member{
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String imgUrl;
    private String googleId;
    private String naverId;

    @Builder
    public Member(String name, String email, String imgUrl, String googleId, String naverId){
        this.name=name;
        this.email=email;
        this.imgUrl=imgUrl;
        this.googleId=googleId;
        this.naverId=naverId;
    }
}
