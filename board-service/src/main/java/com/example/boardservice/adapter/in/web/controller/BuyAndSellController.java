package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.PostBuyAndSellCommand;
import com.example.boardservice.adapter.in.web.command.UpdateBuyAndSellCommand;
import com.example.boardservice.application.port.in.ArticleUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buy-and-sell")
public class BuyAndSellController {

    private final ArticleUseCase articleUseCase;
    private final ObjectMapper objectMapper;

    @Tag(name = "식물거래 게시글 작성", description = "새로운 식물거래 게시글을 작성합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> postBuyAndSellArticle(
            @Schema(
                    description = "json 데이터",
                    example = "{\"title\":\"안녕하세요\",\"content\":\"반갑습니다\"," +
                            "\"price\":\"10000\",\"location\":{\"latitude\":\"37.29\"," +
                            "\"longitude\":\"127.14\"},\"sido\":\"서울시\",\"sigungu\":\"강남구\"," +
                            "\"eupmyundong\":\"역삼동\",\"isFree\":false,\"hashTags\":[\"선인장\",\"다육이\"]," +
                            "\"articleStatus\":\"saved\"}"
            )
            @RequestPart("data") @Valid String requestJson,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles) throws JsonProcessingException {

        PostBuyAndSellCommand command = objectMapper.readValue(requestJson, PostBuyAndSellCommand.class);
        command.setMemberId(PassportHolder.getPassport().memberId());
        command.setImageFiles(imageFiles);

        Long articleId = articleUseCase.postBuyAndSell(command);

        return CommonResponse.okWithMessage("게시글 작성에 성공하였습니다.", articleId);
    }

    @Tag(name = "식물거래 게시글 수정", description = "새로운 식물거래 게시글을 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> updateBuyAndSellArticle(
            @Schema(
                    description = "json 데이터",
                    example = "{\"articleId\":1,\"title\":\"수정\",\"content\":\"수정\"," +
                            "\"price\":\"12000\",\"location\":{\"latitude\":\"37.29\",\"longitude\":\"127.14\"}," +
                            "\"sido\":\"수정\",\"sigungu\":\"수정\",\"eupmyundong\":\"수정\",\"isFree\":true," +
                            "\"hashTags\":[\"수정1\",\"수정2\"]}"
            )
            @RequestPart("data") @Valid String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException {
        UpdateBuyAndSellCommand command = objectMapper.readValue(requestJson, UpdateBuyAndSellCommand.class);
        command.addValue(PassportHolder.getPassport().memberId(), imageFiles);

        Long articleId = articleUseCase.updateBuyAndSell(command);
        return CommonResponse.okWithMessage("게시글 수정에 성공하였습니다.", articleId);
    }

    @Tag(name = "식물거래 게시글 삭제", description = "새로운 식물거래 게시글을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> deleteBuyAndSell(@PathVariable("articleId") Long articleId) {
        Long deletedArticleId = articleUseCase.delete(articleId);
        return CommonResponse.okWithMessage("게시글 삭제에 성공하였습니다.", deletedArticleId);
    }
}
