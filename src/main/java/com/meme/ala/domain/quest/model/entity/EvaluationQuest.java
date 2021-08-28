package com.meme.ala.domain.quest.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "QUEST")
@TypeAlias("EvaluationQuest")
public class EvaluationQuest extends Quest {

    @Builder.Default
    private int status = 0;
}

