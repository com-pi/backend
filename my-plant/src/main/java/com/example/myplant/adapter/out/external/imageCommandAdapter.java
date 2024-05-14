package com.example.myplant.adapter.out.external;

import com.example.myplant.application.port.out.PlantImgCommandPort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class imageCommandAdapter implements PlantImgCommandPort {

    @Override
    public List<String> saveImage(List<MultipartFile> image) {

        // 이미지 버킷에 저장 후 url 변환

        return List.of();
    }
}

