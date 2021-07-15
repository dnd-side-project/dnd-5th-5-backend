package com.meme.ala.domain.member.model.entity;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "member")
public class Member{
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String imgUrl;
    private String googleId;
    private String naverId;
}
