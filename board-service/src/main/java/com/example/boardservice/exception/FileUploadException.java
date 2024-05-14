package com.example.boardservice.exception;

import io.minio.errors.MinioException;
import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException {

    public FileUploadException(String message, MinioException minioException) {
        super(message, minioException);
    }

}
