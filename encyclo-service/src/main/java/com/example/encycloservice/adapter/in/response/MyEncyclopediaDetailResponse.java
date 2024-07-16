package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.PlantBrief;
import lombok.Builder;

import java.util.List;

@Builder
public record MyEncyclopediaDetailResponse(
        Long id,
        Long memberId,
        String title,
        String imageUrl,
        List<PlantBrief> plantCollection
) {
    public static MyEncyclopediaDetailResponse fromDomain(MyEncyclopedia myEncyclopedia) {
        return MyEncyclopediaDetailResponse.builder()
                .id(myEncyclopedia.getId())
                .memberId(myEncyclopedia.getMemberId())
                .title(myEncyclopedia.getTitle())
                .imageUrl(myEncyclopedia.getCoverImageUrl())
                .plantCollection(myEncyclopedia.getPlantCollection())
                .build();
    }
}
