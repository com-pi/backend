package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.ArticleStatusType;
import com.example.boardservice.domain.Location;
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
public class PostBuyAndSellUseCase {

    @NotNull @NotBlank private String title;
    @NotNull @NotBlank private String content;
    @NotNull @Min(0) private Integer price;
    @NotNull private Location location;
    @NotNull private String sido;
    @NotNull private String sigungu;
    @NotNull private String eupmyundong;
    @NotNull private ArticleStatusType articleStatus;
    @NotEmpty private List<MultipartFile> imageFiles;
    private List<String> hashtags;

    public PostBuyAndSellUseCase(String title, String content, Integer price, Location location,
                                 String sido, String sigungu, String eupmyundong,
                                 ArticleStatusType articleStatus, List<MultipartFile> imageFiles,
                                 List<String> hashtags) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyundong = eupmyundong;
        this.articleStatus = articleStatus;
        this.imageFiles = imageFiles;
        this.hashtags = hashtags;
    }
}
