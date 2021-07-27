package com.meme.ala.domain.friend.repository;

import com.meme.ala.domain.friend.model.entity.Friend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends MongoRepository<Friend, ObjectId> {
}
