package com.example.imagemodule.configuration;

import com.example.common.exception.InternalServerException;
import com.example.imagemodule.domain.MinioBucket;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.MinioException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfig {

    private final String endpoint;
    private final String credential;

    public MinioConfig(Environment env) {
        String activeProfile;
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length == 0) {
            activeProfile = "dev";
        } else {
            activeProfile = activeProfiles[0];
        }
        if (activeProfile.equals("local")) {
            endpoint = "http://localhost:9000";
            credential = "compcomp!!";
        } else {
            endpoint = "http://minio:9000";
            credential = "compcomp!!";
        }
    }

    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials("admin", credential)
                .build();
        bucketInitialize(minioClient);
        return minioClient;
    }

    private void bucketInitialize(MinioClient minioClient) {
        try {
            for (MinioBucket bucket : MinioBucket.values()) {
                makeBucketIfAbsent(bucket, minioClient);
            }
        } catch (MinioException e){
            throw new RuntimeException("이미지 버킷 초기화에 실패했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void makeBucketIfAbsent(MinioBucket bucket, MinioClient minioClient)
            throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        boolean isExist = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucket.getBucketName())
                        .build());
        if(!isExist) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket.getBucketName())
                            .objectLock(false)
                            .build()
            );
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket.getBucketName())
                            .config(loadPolicyFile())
                            .build()
            );
        }
    }

    private String loadPolicyFile() {
        try (InputStream inputStream = getClass().getResourceAsStream("/BucketPolicy.json")) {
            if (inputStream == null) {
                throw new InternalServerException("정책 파일을 찾을 수 없습니다.", null);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InternalServerException("정책 파일을 읽어오는데 실패했습니다.", e);
        }
    }

}