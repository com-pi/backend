package com.example.imagemodule.exception;

import io.minio.errors.MinioException;
import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException {

    public FileUploadException(String message, MinioException minioException) {
        super(message, minioException);
    }

}
