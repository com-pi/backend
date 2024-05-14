package com.example.boardservice.configuration;

import com.example.common.exception.InternalServerException;
import com.example.imagemodule.MinioBucket;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.MinioException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfig {

    // Todo endpoint application 상수로

    @Bean
    @Profile("dev")
    public MinioClient minioClientDev(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint("https://bucket.com-p.site")
                .credentials("ADMIN", "compcomp!!")
                .build();
        bucketInitialize(minioClient);
        return minioClient;
    }

    @Bean
    @Profile("local")
    public MinioClient minioClientLocal(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("ADMIN", "compcomp!!")
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
            throw new InternalServerException("이미지 버킷 초기화에 실패했습니다.", e);
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
        try (InputStream inputStream = getClass().getResourceAsStream("/com/example/boardservice/configuration/BucketPolicy.json")) {
            if (inputStream == null) {
                throw new InternalServerException("정책 파일을 찾을 수 없습니다.", null);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InternalServerException("정책 파일을 읽어오는데 실패했습니다.", e);
        }
    }

}