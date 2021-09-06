package com.meme.ala.domain.member.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.TemporalWordList;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSettingMapper;
import com.meme.ala.domain.alacard.repository.BackgroundRepository;
import com.meme.ala.domain.alacard.repository.TemporalWordListRepository;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberCardServiceImpl implements MemberCardService {
    private final AlaCardSaveMapper alaCardSaveMapper;
    private final AlaCardSettingMapper alaCardSettingMapper;
    private final MemberRepository memberRepository;
    private final BackgroundRepository backgroundRepository;
    private final TemporalWordListRepository temporalWordListRepository;
    private final MemberService memberService;
    private final MemberCardSettingService memberCardSettingService;
    private final AggregationService aggregationService;

    @Override
    @Transactional
    public void assignCard(Member member, int num) {
        List<AlaCard> memberAlaCardList = member.getAlaCardList();

        List<AlaCard> selectedAlaCardList = memberCardSettingService.selectAlaCardList(memberAlaCardList, num);

        List<AlaCardSetting> alaCardSettingList = memberCardSettingService.initAlaCardSettingList();

        member.assignAlaCardSettingPairList(selectedAlaCardList, alaCardSettingList);

        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void setTemporalWordList(String cookieId, String nickname, Boolean shuffle) {
        Member member = memberService.findByNickname(nickname);

        List<AlaCard> alaCardList = member.getAlaCardList();

        List<SelectionWordDto> wordDtoList = alaCardSaveMapper.alaCardListToSelectionWordDtoList(alaCardList);

        if (shuffle) Collections.shuffle(wordDtoList);

        TemporalWordList temporalWordList = TemporalWordList.builder().cookieId(cookieId).wordDtoList(wordDtoList).build();

        temporalWordListRepository.save(temporalWordList);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SelectionWordDto> getWordList(String cookieId) {
        TemporalWordList temporalWordList = temporalWordListRepository.findByCookieId(cookieId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.INVALID_COOKIE));

        return temporalWordList.getWordDtoList();
    }

    @Override
    @Transactional
    public void saveSetting(Member member, AlaCardSettingDto alaCardSettingDto) {
        for (AlaCardSettingPair alaCardSettingPair : member.getAlaCardSettingPairList()) {
            if (alaCardSettingPair.getAlaCard().getId().toHexString()
                    .equals(alaCardSettingDto.getAlaCardId())) {
                alaCardSettingMapper.updateAlaCardSettingFromDto(alaCardSettingDto, alaCardSettingPair.getAlaCardSetting());
                if (alaCardSettingDto.getBackgroundImgUrl() != null) {
                    String backgroundImgUrl = alaCardSettingDto.getBackgroundImgUrl().replace("/thumb/", "/static/");
                    alaCardSettingPair.getAlaCardSetting().getBackground().setImgUrl(backgroundImgUrl);
                    Background background = backgroundRepository.findByImgUrl(backgroundImgUrl)
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BACKGROUND_NOT_FOUND));
                    alaCardSettingPair.getAlaCardSetting().getBackground()
                            .setFontColor(background.getFontColor());
                    alaCardSettingPair.getAlaCardSetting().getBackground()
                            .setCategory(background.getCategory());
                }
                break;
            }
        }
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void obtainCard(Member member) {
        assignCard(member, 1);
        aggregationService.addAggregation(member);
    }
}
