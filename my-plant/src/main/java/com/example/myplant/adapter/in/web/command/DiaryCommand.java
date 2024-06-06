package com.example.myplant.adapter.in.web.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DiaryCommand {
    private Long id;
    private Long plantId;
    private String title;
    private String content;
    private LocalDate date;
    private boolean isPublic;
    private List<MultipartFile> images; // 이미지 Url 리스트
    private List<String> managementTypes; // 관리 유형 리스트

}