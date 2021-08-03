package com.meme.ala.domain.alacard.repository;

import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlaCardSettingRepository extends MongoRepository<AlaCardSetting, ObjectId> {
    List<AlaCardSetting> findAll();
}