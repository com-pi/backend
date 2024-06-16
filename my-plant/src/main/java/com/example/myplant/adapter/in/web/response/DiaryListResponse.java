package com.example.myplant.adapter.in.web.response;

import java.util.List;

public record DiaryListResponse(
        List<DiaryResponse> diaryResponseList
) {
    public static DiaryListResponse from(List<DiaryResponse> diaryResponseList) {
        return new DiaryListResponse(diaryResponseList);
    }
}
