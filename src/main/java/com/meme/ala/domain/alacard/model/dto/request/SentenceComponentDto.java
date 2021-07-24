package com.meme.ala.domain.alacard.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SentenceComponentDto {
    private String josa;
    private String eomi;
}
