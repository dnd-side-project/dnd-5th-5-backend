package com.meme.ala.domain.friend.model.entity;

import com.meme.ala.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Document("FRIEND")
public class Friend {
    @Id
    private ObjectId id;

    @DBRef(lazy = true)
    private Member member;

    @Builder.Default
    private List<ObjectId> pendings=new LinkedList<>();

    @Builder.Default
    private List<ObjectId> friends=new LinkedList<>();
}
