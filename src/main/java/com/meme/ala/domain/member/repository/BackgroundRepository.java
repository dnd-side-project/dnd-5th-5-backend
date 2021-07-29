package com.meme.ala.domain.member.repository;

import com.meme.ala.domain.member.model.entity.cardSetting.Background;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BackgroundRepository extends MongoRepository<Background, ObjectId> {
}