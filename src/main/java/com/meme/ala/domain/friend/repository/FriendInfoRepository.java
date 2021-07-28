package com.meme.ala.domain.friend.repository;

import com.meme.ala.domain.friend.model.entity.FriendInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendInfoRepository extends MongoRepository<FriendInfo, ObjectId> {
}
