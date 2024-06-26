package com.example.encycloservice.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "plantCollection")
@EqualsAndHashCode(of = {"memberId", "name"})
public class MyEncyclopedia {

    private Long memberId;
    private String name;
    private String imageUrl;
    private List<PlantBrief> plantCollection;

}
