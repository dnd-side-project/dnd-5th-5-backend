package com.meme.ala.domain.alacard.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "ALA_CARD")
public class AlaCard {
    @Id
    private ObjectId id;

    private String bigCategory;

    @Singular("middleCategoryItem")
    private List<MiddleCategory> middleCategoryList;
}
