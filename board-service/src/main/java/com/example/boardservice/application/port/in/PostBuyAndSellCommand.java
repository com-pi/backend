package com.example.boardservice.application.port.in;


import com.example.boardservice.common.SelfValidating;
import com.example.boardservice.domain.ArticleStatusType;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostBuyAndSellCommand extends SelfValidating<PostBuyAndSellCommand> {

    @Parameter(description = "게시글 제목", required = true)
    @NotNull
    @NotBlank
    private String title;

    @Parameter(name = "내용", required = true)
    @NotNull
    @NotBlank
    private String content;

    @Parameter(description = "가격", required = true)
    @NotNull
    @Min(0)
    private Integer price;

    @Parameter(description = "시도 행정지역 id", required = true)
    @NotNull
    private Long sidoId;

    @Parameter(description = "시군구 행정지역 id", required = true)
    private Long sigunguId;

    @Parameter(description = "읍면동 행정지역 id", required = true)
    private Long eupmyundongId;

    @Parameter(description = "상태 (임시저장 여부)", required = true)
    private ArticleStatusType articleStatus;

    private List<MultipartFile> imageFiles;

    @Parameter(description = "해시태그 배열")
    private List<String> hashtags;

}
