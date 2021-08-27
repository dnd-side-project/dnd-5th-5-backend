package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberCardSettingServiceImpl implements MemberCardSettingService {
    private final AlaCardService alaCardService;

    public List<AlaCard> selectAlaCardList(List<AlaCard> memberAlaCardList, int num) {
        List<AlaCard> alaCardList = alaCardService.getAlaCardList();

        Collections.shuffle(alaCardList);

        return alaCardList.stream()
                        .filter(alaCard -> !alaCard.getSpecial())
                        .filter(alaCard -> !memberAlaCardList.contains(alaCard))
                        .limit(num)
                        .collect(Collectors.toList());
    }

    public List<AlaCardSetting> initAlaCardSettingList(){
        List<AlaCardSetting> alaCardSettingList = alaCardService.getBackgrounds()
                                                                .stream()
                                                                .map(background -> AlaCardSetting.builder()
                                                                        .background(background)
                                                                        .isOpen(true)
                                                                        .build())
                                                                .collect(Collectors.toList());

        Collections.shuffle(alaCardSettingList);

        return alaCardSettingList;
    }

}
