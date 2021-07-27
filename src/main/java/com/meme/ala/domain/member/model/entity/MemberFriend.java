package com.meme.ala.domain.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "FRIENDS")
public class MemberFriend {

    @Id
    private ObjectId memberId;

    private List<ObjectId> friends;

    private List<ObjectId> pendings;
}
