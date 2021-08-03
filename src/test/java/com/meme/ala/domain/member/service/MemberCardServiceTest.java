package com.meme.ala.domain.member.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberCardServiceTest {
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private AlaCardRepository alaCardRepository;
    @Autowired
    private MemberCardService memberCardService;
    @MockBean
    private AlaCardService alaCardService;
    @Value("${member.alacardnum}")
    private int defaultCardNum;

    @DisplayName("멤버 카드 할당 테스트")
    @Test
    public void 멤버_카드_할당_테스트() throws Exception {
        List<AlaCard> alaCardList = new ArrayList<>();
        for (int i = 0; i < defaultCardNum * 2; i++) {
            AlaCard alaCard = AlaCard.builder().bigCategory("test" + i).build();
            alaCardList.add(alaCard);
        }
        when(memberRepository.save(any(Member.class))).then(AdditionalAnswers.returnsFirstArg());
        when(memberRepository.existsMemberByMemberSettingNickname(any(String.class))).thenReturn(false);
        when(alaCardRepository.findAll()).thenReturn(alaCardList);
        when(alaCardService.getAlaCardSettings()).thenReturn(Arrays.asList(EntityFactory.testAlaCardSetting()));


        Member testMember = Member.builder().build();
        memberCardService.assignCard(testMember, defaultCardNum);

        testMember.getAlaCardSettingPairList()
                .forEach(alaCardSettingPair -> System.out.println(alaCardSettingPair.getAlaCard().getBigCategory()));
        assertThat(testMember.getAlaCardSettingPairList().size()).isEqualTo(defaultCardNum);
    }
}
