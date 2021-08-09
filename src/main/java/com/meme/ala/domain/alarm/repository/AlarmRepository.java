package com.meme.ala.domain.alarm.repository;

import com.meme.ala.domain.alarm.model.entity.Alarm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlarmRepository extends MongoRepository<Alarm, ObjectId> {
    List<Alarm> findAllByMemberIdOrderByCreatedAtDesc(ObjectId memberId);
}
