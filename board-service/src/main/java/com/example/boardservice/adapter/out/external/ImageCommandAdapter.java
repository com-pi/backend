package com.example.boardservice.adapter.out.external;

import com.example.boardservice.application.port.out.ImageCommandPort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageCommandAdapter implements ImageCommandPort {

    @Override
    public List<String> saveImages(List<MultipartFile> images) {

        // 이미지 버킷에 저장후 url 반환

        return List.of();
    }

}
