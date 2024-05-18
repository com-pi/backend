package com.example.myplant.application.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploadPort {
    List<String> uploadImages(List<MultipartFile> images);
}
