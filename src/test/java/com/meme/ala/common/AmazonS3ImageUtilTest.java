package com.meme.ala.common;

import com.meme.ala.common.utils.AmazonS3ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AmazonS3ImageUtilTest {
    @Autowired
    AmazonS3ImageUtil amazonS3ImageUtil;
    @Test
    public void 아마존_이미지_유틸_테스트() throws Exception {
        List<String> images = amazonS3ImageUtil.getObjectItemUrls();
        for(String image:images){
            System.out.println(image);
        }
    }
}