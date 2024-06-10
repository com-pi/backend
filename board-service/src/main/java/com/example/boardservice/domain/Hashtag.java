package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hashtag {

    private Long hashtagId;

    private String name;

    public static Hashtag ofName(String name) {
        return Hashtag.builder()
                .name(name)
                .build();
    }

}
