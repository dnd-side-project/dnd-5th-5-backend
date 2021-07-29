package com.meme.ala.domain.aggregation.repository;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregationRepository extends MongoRepository<Aggregation, ObjectId> {
    Optional<Aggregation> findByMemberId(ObjectId memberId);
}