package com.meme.ala.domain.aggregation.repository;

import com.meme.ala.domain.aggregation.model.entity.UserCount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCountRepository extends MongoRepository<UserCount, ObjectId> {
}
