package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Location;
import com.example.common.baseentity.SelfValidating;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class UpdateBuyAndSellCommand extends SelfValidating<UpdateBuyAndSellCommand> {

    private final Long articleId;
    private Long memberId;
    private final String title;
    private final String content;
    private final Integer price;
    private final Location location;
    private final String sido;
    private final String sigungu;
    private final String eupmyundong;
    private final List<String> hashTags;
    private List<MultipartFile> imageFiles;

    public UpdateBuyAndSellCommand(Long articleId, Long memberId, String title, String content, Integer price,
                                   Location location, String sido, String sigungu, String eupmyundong,
                                   List<String> hashTags, List<MultipartFile> imageFiles) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyundong = eupmyundong;
        this.hashTags = hashTags;
        this.imageFiles = imageFiles;
        validateSelf();
    }

    public void addValue(Long memberId, List<MultipartFile> imageFiles) {
        this.memberId = memberId;
        this.imageFiles = imageFiles;
    }
}
