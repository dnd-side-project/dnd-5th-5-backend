package com.meme.ala.domain.aggregation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "USERCOUNT")
public class UserCount {
    ObjectId id;
    private int count;
}
