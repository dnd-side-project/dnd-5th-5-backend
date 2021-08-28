package com.meme.ala.domain.quest.repository;

import com.meme.ala.domain.quest.model.entity.Quest;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QuestRepository extends MongoRepository<Quest, ObjectId> {
    Optional<Quest> findByMemberIdAndCategory(ObjectId memberId, QuestCategory category);
}
