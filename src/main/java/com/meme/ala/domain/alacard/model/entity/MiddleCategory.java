package com.meme.ala.domain.alacard.model.entity;

import com.meme.ala.common.utils.UnicodeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MiddleCategory {
    private String middleCategoryName;

    private String hint;

    private SentenceComponent sentenceComponent;

    private String prefix;

    private String form;

    @Singular("wordItem")
    private List<Word> wordList;

    public String getParsedForm(String word) {
        String delimeter = "/";
        String[] formTokens = form.split(" ");
        String baseToken = null;
        String resultJosa;
        if (!form.contains(delimeter))
            return form.replace("???", word);
        for (String token : formTokens) {
            if (token.contains(delimeter) && token.contains("???")) {
                baseToken = token;
                break;
            }
        }
        String josas = baseToken.split("\\?\\?\\?")[1];
        String[] josaTokens = josas.split(delimeter);
        if (UnicodeUtil.isWithBatchim(word))
            resultJosa = josaTokens[0];
        else
            resultJosa = josaTokens[1];
        String result = form.replace("???", word);
        result = result.replace(josas, resultJosa);
        return result;
    }
}
