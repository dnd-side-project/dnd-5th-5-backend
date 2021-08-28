package com.meme.ala.domain.member.model.entity;

import com.meme.ala.core.auth.jwt.Authority;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "MEMBER")
public class Member {
    @Id
    private ObjectId id;

    private String email;

    private String providerId;

    private MemberSetting memberSetting;

    @Builder.Default
    private String authority = Authority.ROLE_USER;

    @Builder.Default
    private List<AlaCardSettingPair> alaCardSettingPairList = new LinkedList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public List<AlaCard> getAlaCardList() {
        return this.getAlaCardSettingPairList()
                    .stream()
                    .map(AlaCardSettingPair::getAlaCard)
                    .collect(Collectors.toList());
    }

    public void assignAlaCardSettingPairList(List<AlaCard> selectedAlaCardList, List<AlaCardSetting> alaCardSettingList) {
        for (int i = 0; i < selectedAlaCardList.size(); i++) {
            this.getAlaCardSettingPairList()
                    .add(AlaCardSettingPair.builder()
                                            .alaCard(selectedAlaCardList.get(i))
                                            .alaCardSetting(alaCardSettingList.get(i % alaCardSettingList.size()))
                                            .build()
                    );
        }
    }
}