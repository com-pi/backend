package com.example.boardservice.adapter.in.web;

import com.example.boardservice.application.port.in.PostArticleUseCase;
import com.example.boardservice.domain.ArticleStatusType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyAndSellController {

    private final PostArticleUseCase postArticleUseCase;

    @Tag(name = "식물거래 게시글 작성", description = "새로운 식물거래 게시글을 작성합니다.")
    @PostMapping(value = "/buy-and-sell", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postBuyAndSell(
            @Parameter(description = "제목", required = true) String title,
            @Parameter(description = "내용", required = true) String content,
            @Parameter(description = "가격", required = true) Integer price,
            @Parameter(description = "시도 행정지역 id", required = true) Long sidoId,
            @Parameter(description = "시군구 행정지역 id", required = true) Long sigunguId,
            @Parameter(description = "읍면동 행정지역 id", required = true) Long eupmyundongId,
            @Parameter(description = "상태 (임시저장 여부)", required = true) String articleStatus,
            @Parameter(description = "이미지 배열") @RequestPart("imageFiles") List<MultipartFile> imageFiles,
            @Parameter(description = "해시태그 배열") String[] hashtags) {

        PostBuyAndSellRequest request = PostBuyAndSellRequest.builder()
                .title(title)
                .content(content)
                .price(price)
                .sidoId(sidoId)
                .sigunguId(sigunguId)
                .eupmyundongId(eupmyundongId)
                .articleStatus(ArticleStatusType.of(articleStatus))
                .imageFiles(imageFiles)
                .hashtags(List.of(hashtags))
                .build();

        postArticleUseCase.postBuyAndSell(request);
    }

    @Tag(name = "식물거래 게시글 작성", description = "새로운 식물거래 게시글을 작성합니다.")
    @PostMapping(value = "/v2/buy-and-sell", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postBuyAndSell2(@ParameterObject @ModelAttribute PostBuyAndSellRequest d)  {

    }

}
