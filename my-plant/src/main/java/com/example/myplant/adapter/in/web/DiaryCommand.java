package com.example.myplant.adapter.in.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryCommand {

    private String title;
    private String content;
    private String imageUrl;
    private String visibilityCode; // 공개/비공개 설정 todo
}