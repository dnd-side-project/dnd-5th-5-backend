package com.meme.ala.domain.alarm.repository;

import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends MongoRepository<Alarm, ObjectId> {
    List<Alarm> findAllByMemberIdOrderByCreatedAtDesc(ObjectId memberId);
    Optional<FriendAlarm> findAlarmByMemberIdAndFriendId(ObjectId memberId, ObjectId friendId);
}
