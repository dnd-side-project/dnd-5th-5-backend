package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;

import java.util.List;

public interface MemberCardSettingService {
    List<AlaCard> selectAlaCardList(List<AlaCard> memberAlaCardList, int num);
    List<AlaCardSetting> initAlaCardSettingList();
}
