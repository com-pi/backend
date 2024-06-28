package com.example.encycloservice.domain;

import lombok.Builder;

@Builder
public record MyEncyclopediaCreate (
        String title,
        Long memberId
) {

}
