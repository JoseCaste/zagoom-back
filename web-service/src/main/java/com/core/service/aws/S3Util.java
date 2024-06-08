package com.core.service.aws;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class S3Util {

    private static S3Client s3Client;
    private ApplicationContext applicationContext;

    @Autowired
    public void getApplicationContext(ApplicationContext context, S3Client s3Client_ ) {
        this.applicationContext = context;
        s3Client = s3Client_;
    }

    public static void uploadFile(String bucketName, String key, MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile.getContentType());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, Paths.get(file.getPath()));
    }

    public static void downloadFile(String bucketName, String key, String downloadFilePath) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.getObject(getObjectRequest, Paths.get(downloadFilePath));
    }

}
