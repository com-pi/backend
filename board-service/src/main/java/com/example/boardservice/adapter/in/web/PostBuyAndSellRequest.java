package com.example.boardservice.adapter.in.web;

import com.example.boardservice.common.SelfValidating;
import com.example.boardservice.domain.ArticleStatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class PostBuyAndSellRequest extends SelfValidating<PostBuyAndSellRequest> {

    @NotNull @NotBlank private String title;
    @NotNull @NotBlank private String content;
    @NotNull @Min(0) private Integer price;
    @NotNull private Long sidoId;
    @NotNull private Long sigunguId;
    @NotNull private Long eupmyundongId;
    @NotNull private ArticleStatusType articleStatus;
    @NotEmpty private List<MultipartFile> imageFiles;
    private List<String> hashtags;

    public PostBuyAndSellRequest(String title, String content, Integer price,
                                 Long sidoId, Long sigunguId, Long eupmyundongId,
                                 ArticleStatusType articleStatus, List<MultipartFile> imageFiles,
                                 List<String> hashtags) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.sidoId = sidoId;
        this.sigunguId = sigunguId;
        this.eupmyundongId = eupmyundongId;
        this.articleStatus = articleStatus;
        this.imageFiles = imageFiles;
        this.hashtags = hashtags;
        super.validateSelf();
    }
}
