package com.meme.ala.domain.quest.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.quest.model.entity.Quest;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import com.meme.ala.domain.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestServiceImpl implements QuestService{
    private final QuestRepository questRepository;

    @Override
    public Quest findByMemberIdAndCategory(ObjectId memberId, QuestCategory category) {
        return questRepository.findByMemberIdAndCategory(memberId, category)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public Quest save(Quest quest){
        return questRepository.save(quest);
    }
}
