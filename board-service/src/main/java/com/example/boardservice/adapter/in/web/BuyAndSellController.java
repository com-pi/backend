package com.example.boardservice.adapter.in.web;

import com.example.boardservice.application.port.in.PostArticleUseCase;
import com.example.common.annotation.Authenticate;
import com.example.boardservice.security.PassportHolder;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyAndSellController {

    private final PostArticleUseCase postArticleUseCase;
    private final ObjectMapper objectMapper;

    @Tag(name = "식물거래 게시글 작성", description = "새로운 식물거래 게시글을 작성합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping(value = "/buy-and-sell", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> postBuyAndSellArticle(
            @Schema(
                    description = "json 데이터",
                    example = "{\"title\":\"안녕하세요\",\"content\":\"반갑습니다\"," +
                            "\"price\":\"10000\",\"location\":{\"latitude\":\"37.29\"," +
                            "\"longitude\":\"127.14\"},\"sido\":\"서울시\",\"sigungu\":\"강남구\"," +
                            "\"eupmyundong\":\"역삼동\",\"hashTags\":[\"선인장\",\"다육이\"]," +
                            "\"articleStatus\":\"saved\"}"
            )
            @RequestPart("data") String jsonData,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles) throws JsonProcessingException {

        PostBuyAndSellCommand command = objectMapper.readValue(jsonData, PostBuyAndSellCommand.class);
        command.setMemberId(PassportHolder.getPassport().memberId());
        command.setImageFiles(imageFiles);

        Long articleId = postArticleUseCase.postBuyAndSell(command);

        return CommonResponse.okWithMessage("게시글 작성에 성공하였습니다.", articleId);
    }

}
