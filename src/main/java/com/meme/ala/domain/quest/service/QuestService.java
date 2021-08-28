package com.meme.ala.domain.quest.service;

import com.meme.ala.domain.quest.model.entity.Quest;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import org.bson.types.ObjectId;

public interface QuestService {
    Quest findByMemberIdAndCategory(ObjectId memberId, QuestCategory category);
    Quest save(Quest quest);
}
