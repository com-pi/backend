package com.example.boardservice.adapter.in.web;

import com.example.boardservice.domain.Location;
import com.example.common.baseentity.SelfValidating;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@Getter
@Builder
public class PostBuyAndSellCommand extends SelfValidating<PostBuyAndSellCommand> {

    @Setter
    private Long memberId;
    private final String title;
    private final String content;
    private final Integer price;
    private final Location location;
    private final String sido;
    private final String sigungu;
    private final String eupmyundong;
    private final List<String> hashTags;
    @Setter
    private List<MultipartFile> imageFiles;

    public PostBuyAndSellCommand(Long memberId, String title, String content, Integer price,
                                 Location location, String sido, String sigungu, String eupmyundong,
                                 List<String> hashTags, List<MultipartFile> imageFiles) {
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

}
