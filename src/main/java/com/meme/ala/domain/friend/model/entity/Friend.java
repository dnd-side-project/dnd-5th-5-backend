package com.meme.ala.domain.friend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@Document(collection = "FRIEND")
public class Friend {

    @Id
    private ObjectId memberId;

    @Builder.Default
    private List<ObjectId> friends = new LinkedList<>();

    @Builder.Default
    private List<ObjectId> pendings = new LinkedList<>();
}
