package com.meme.ala.domain.alacard.repository;

import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BackgroundRepository extends MongoRepository<Background, ObjectId> {
    List<Background> findAll();
}
