package com.example.boardservice.configuration;

import com.example.common.exception.InternalServerException;
import com.example.imagemodule.MinioBucket;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class MinioConfig {

    // Todo endpoint application 상수로

    private final String classPath= "board-service/src/main/java/com/example/boardservice/configuration/";

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
                .credentials("ADMIN", "compcomp")
                .build();
        bucketInitialize(minioClient);
        return minioClient;
    }

    private void bucketInitialize(MinioClient minioClient) {
        try {
            for(MinioBucket bucket : MinioBucket.values()){
                makeBucketIfAbsent(bucket, minioClient);
            }
        } catch (Exception e){
            throw new InternalServerException("이미지 버킷 초기화에 실패했습니다.", e);
        }
    }

    private void makeBucketIfAbsent(MinioBucket bucket, MinioClient minioClient) throws Exception {
        boolean isExist = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucket.getBucketName())
                        .build());
        if(!isExist) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket.getBucketName())
                            .build()
            );
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket.getBucketName())
                            .config(loadPolicyFile(classPath + "BucketPolicy.json"))
                            .build()
            );
        }
    }

    private String loadPolicyFile(String filePath) {
        Path jsonFilePath = Path.of(filePath);
        try {
            return Files.readString(jsonFilePath);
        } catch (IOException e) {
            throw new InternalServerException("이미지 버킷 정책파일을 읽어오는데 실패 했습니다.", e);
        }
    }

}