package com.meme.ala.domain.aggregation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "AGGREGATION")
public class Aggregation {
    @Id
    private ObjectId id;

    private ObjectId memberId;

    @Builder.Default
    List<WordCount> wordCountList = new LinkedList<>();
}