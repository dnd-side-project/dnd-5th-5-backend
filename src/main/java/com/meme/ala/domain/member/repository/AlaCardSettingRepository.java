package com.meme.ala.domain.member.repository;

import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlaCardSettingRepository extends MongoRepository<AlaCardSetting, ObjectId> {
}