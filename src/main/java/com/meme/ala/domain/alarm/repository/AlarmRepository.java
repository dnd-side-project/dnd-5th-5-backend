package com.meme.ala.domain.alarm.repository;

import com.meme.ala.domain.alarm.model.entity.Alarm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmRepository extends MongoRepository<Alarm, ObjectId> {
}
