package com.example.boardservice.domain;

import com.example.common.domain.Address;
import lombok.*;

import java.util.List;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "generate")
public class PlantSitter {

    private Long articleId;
    private String title;
    private String content;
    private CareInstruction careInstruction;
    private Integer price;
    private Address meetingArea;
    private List<String> imageUrls;

}
