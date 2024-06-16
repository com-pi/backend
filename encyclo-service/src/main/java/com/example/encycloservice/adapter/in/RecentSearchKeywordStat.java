package com.example.encycloservice.adapter.in;

import lombok.Builder;

import java.util.List;

@Builder
public record RecentSearchKeywordStat(
        int totalElement,
        int totalPage,
        List<RecentSearchKeywordRank> results
) {
    @Builder
    public record RecentSearchKeywordRank(
            int rank,
            String keyword
    ){

    }
}
