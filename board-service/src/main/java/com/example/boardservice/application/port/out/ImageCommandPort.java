package com.example.boardservice.application.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageCommandPort {

    List<String> saveImages(List<MultipartFile> images);

}
