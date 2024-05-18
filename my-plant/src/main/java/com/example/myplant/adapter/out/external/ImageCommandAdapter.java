package com.example.myplant.adapter.out.external;

import com.example.myplant.application.port.out.ImageUploadPort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageCommandAdapter implements ImageUploadPort {

    @Override
    public List<String> uploadImages(List<MultipartFile> images) {
        // 예시: 실제 업로드 로직을 구현
        return images.stream()
                .map(path -> "https://example.com/images/" + path)
                .collect(Collectors.toList());
    }
}