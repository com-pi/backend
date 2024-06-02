package com.example.imagemodule.adapter;

import com.example.common.exception.InternalServerException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.ImageAndThumbnail;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.exception.FileUploadException;
import com.example.imagemodule.util.ObjectUrlMapper;
import com.example.imagemodule.util.ThumbnailGenerator;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.imagemodule.domain.MinioBucket.PROFILE_IMAGE;
import static com.example.imagemodule.domain.MinioBucket.TEMPORAL;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCommandAdapter implements ImageCommand {

    private final MinioClient minioClient;
    private final ObjectUrlMapper objectUrlMapper;

    @Override
    public List<String> saveImages(SaveImagesCommand command) {
        List<String> uploadedObjectName = new ArrayList<>();
        try {
            uploadedObjectName = uploadFilesTemporally(command.imageFiles());
            moveToFinalLocation(uploadedObjectName, command.bucket());
        } finally {
            rollbackFiles(uploadedObjectName);
        }
        return uploadedObjectName;
    }

    @Override
    public ImageAndThumbnail saveProfileImage(MultipartFile image){
        List<String> uploadedObjectName = new ArrayList<>();
        try {
            uploadedObjectName = uploadFilesTemporally(image, ThumbnailGenerator.createThumbnail(image));
            moveToFinalLocation(uploadedObjectName, MinioBucket.PROFILE_IMAGE);
        } catch (IOException e) {
            throw new RuntimeException("썸네일 이미지 생성중 에러 발생");
        } finally {
            rollbackFiles(uploadedObjectName);
        }
        List<String> urls = uploadedObjectName.stream()
                .map(object -> objectUrlMapper.toUrl(object, PROFILE_IMAGE))
                .toList();
        return ImageAndThumbnail.of(urls);
    }

    private List<String> uploadFilesTemporally(MultipartFile... imageFiles) {
        return uploadFilesTemporally(List.of(imageFiles));
    }

    private List<String> uploadFilesTemporally(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(file -> {
            try {
                String fileName = getDateTimePrefix() + file.getOriginalFilename();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(TEMPORAL.getBucketName())
                                .object(fileName)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .build());
                return fileName;
            } catch (MinioException e) {
                throw new FileUploadException("파일 업로드 중 오류가 발생하였습니다.", e);
            } catch (Exception e) {
                throw new InternalServerException("파일 업로드 중 알 수 없는 오류가 발생하였습니다.", e);
            }
        }).toList();
    }

    private void moveToFinalLocation(List<String> tempObjectNames, MinioBucket bucket) {
        tempObjectNames.forEach(objectName -> {
            try {
                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucket.getBucketName())
                                .object(objectName)
                                .source(
                                        CopySource.builder()
                                                .bucket(TEMPORAL.getBucketName())
                                                .object(objectName)
                                                .build())
                                .build());
            } catch (MinioException e) {
                throw new FileUploadException("파일 카피 중 오류가 발생하였습니다.", e);
            } catch (Exception e) {
                throw new InternalServerException("파일 카피 중 알 수 없는 오류가 발생하였습니다.", e);
            }
        });
    }

    private void rollbackFiles(List<String> fileNames) {
        List<DeleteObject> deleteObjects = fileNames.stream().map(DeleteObject::new).toList();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(TEMPORAL.getBucketName())
                        .objects(deleteObjects)
                        .build());
        for(Result<DeleteError> result : results) {
            try {
                DeleteError deleteError = result.get();
                log.info("이미지 롤백 에러 : {}", deleteError.message());
            } catch (Exception ignored){
            }
        }

    }

    private String getDateTimePrefix() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() % 100;
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        return String.format("%02d%02d%02d_%02d%02d%02d_", year, month, day, hour, minute, second);
    }
}
