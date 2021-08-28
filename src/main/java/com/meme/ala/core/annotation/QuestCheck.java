package com.meme.ala.core.annotation;

import com.meme.ala.domain.quest.model.entity.QuestCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestCheck {
    QuestCategory category() default QuestCategory.EVALUATION;
}
