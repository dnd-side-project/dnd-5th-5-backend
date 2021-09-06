package com.meme.ala.domain.alacard.entity;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MiddleCategoryTest {
    private MiddleCategory middleCategory = EntityFactory.testAlaCard()
            .getMiddleCategoryList().get(0);
    private MiddleCategory middleCategoryWithoutSlash = EntityFactory.testAlaCard()
            .getMiddleCategoryList().get(1);

    @Test
    public void getParsedFormTest() {
        String withBatchim = middleCategory.getParsedForm("수영");
        String withoutBatchim = middleCategory.getParsedForm("공부");
        String withQuestion = middleCategory.getParsedForm("???");

        assertEquals(withBatchim, "수영을 하는 것을 좋아");
        assertEquals(withoutBatchim, "공부를 하는 것을 좋아");
        assertEquals(withQuestion, "???을 하는 것을 좋아");
    }

    @Test
    public void getParsedFormWithoutSlashTest() {
        String withBatchim = middleCategoryWithoutSlash.getParsedForm("???");
        String withoutBatchim = middleCategoryWithoutSlash.getParsedForm("공부");

        assertEquals(withBatchim, "??? 하는 것을 좋아");
        assertEquals(withoutBatchim, "공부 하는 것을 좋아");
    }
}
