package com.meme.ala.domain.alacard.repository;

import com.meme.ala.domain.alacard.model.entity.TemporalWordList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemporalWordListRepository extends MongoRepository<TemporalWordList, ObjectId> {
    Optional<TemporalWordList> findByCookieId(String cookieId);
}
