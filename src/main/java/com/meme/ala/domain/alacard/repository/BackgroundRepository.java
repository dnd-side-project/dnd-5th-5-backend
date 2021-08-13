package com.meme.ala.domain.alacard.repository;

import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackgroundRepository extends MongoRepository<Background, ObjectId> {
    List<Background> findAll();
}
