package com.meme.ala.domain.alacard.repository;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlaCardRepository extends MongoRepository<AlaCard, ObjectId> {
    List<AlaCard> findAllBySpecial(Boolean special);
}
