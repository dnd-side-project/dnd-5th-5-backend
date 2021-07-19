package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlaCardServiceImpl implements AlaCardService{
    private final AlaCardRepository alaCardRepository;

    @Transactional
    @Override
    public void save(AlaCard alaCard) {
        alaCardRepository.save(alaCard);
    }
}
