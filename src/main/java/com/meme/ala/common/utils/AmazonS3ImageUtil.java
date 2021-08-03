package com.meme.ala.common.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AmazonS3ImageUtil {
    private final AmazonS3 s3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.bucket.url}")
    private String s3Url;
    private String folderName = "static/";

    public List<String> getObjectItemUrls() throws Exception {
        List<String> objectList = new ArrayList<>();
        ObjectListing objects = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderName));
        for (S3ObjectSummary s : objects.getObjectSummaries()) {
            objectList.add(s3Url + "/" + s.getKey());
        }
        objectList.remove(0);
        return objectList;
    }
}