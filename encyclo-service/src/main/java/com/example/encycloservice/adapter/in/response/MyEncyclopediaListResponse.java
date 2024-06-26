package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.MyEncyclopedia;
import lombok.Builder;

import java.util.List;

@Builder
public record MyEncyclopediaListResponse(
        List<MyEncyclopediaResponse> myEncyclopediaList
) {
    @Builder
    public record MyEncyclopediaResponse(
            Long id,
            String title,
            String coverImageUrl,
            int plantCount
    ) {
        public static MyEncyclopediaResponse fromDomain(MyEncyclopedia myEncyclopedia) {
            return MyEncyclopediaResponse.builder()
                    .id(myEncyclopedia.getId())
                    .title(myEncyclopedia.getTitle())
                    .coverImageUrl(myEncyclopedia.getCoverImageUrl())
                    .plantCount(myEncyclopedia.getPlantCollection().size())
                    .build();
        }
    }
}
