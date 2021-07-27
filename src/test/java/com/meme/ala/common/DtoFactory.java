package com.meme.ala.common;

import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.request.MiddleCategoryDto;
import com.meme.ala.domain.alacard.model.dto.request.SentenceComponentDto;
import com.meme.ala.domain.alacard.model.dto.request.WordDto;
import com.meme.ala.domain.member.model.dto.MemberFriendDto;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;

public class DtoFactory {
    public static MemberPrincipalDto testMemberPrincipalDto() {
        return MemberPrincipalDto.builder()
                .email("test@gmail.com")
                .nickname("testNickname")
                .statusMessage("너 자신을 ala")
                .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                .isOpen(true)
                .build();
    }

    public static AlaCardSaveDto alaCardSaveDto(){
        return AlaCardSaveDto.builder()
                .bigCategory("test")
                .middleCategoryItem(MiddleCategoryDto.builder()
                        .middleCategoryName("testMiddle")
                        .hint("testHint")
                        .prefix("???는 ")
                        .form("???하는 것을 좋아")
                        .sentenceComponent(SentenceComponentDto.builder().eomi("한다").josa("하고, ").build())
                        .wordItem(WordDto.builder().wordName("공부").build())
                        .wordItem(WordDto.builder().wordName("수영").build())
                        .wordItem(WordDto.builder().wordName("테스트").build()).build()
                ).build();
    }

    public static MemberFriendDto testMemberFriendDto() {
        return MemberFriendDto.builder()
                .nickname("testNickname")
                .statusMessage("너 자신을 ala")
                .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                .build();
    }
}
