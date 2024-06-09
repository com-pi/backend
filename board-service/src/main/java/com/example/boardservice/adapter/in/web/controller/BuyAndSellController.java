package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.request.DeleteBuyAndSellRequest;
import com.example.boardservice.adapter.in.web.request.PostBuyAndSellRequest;
import com.example.boardservice.adapter.in.web.request.UpdateBuyAndSellRequest;
import com.example.boardservice.adapter.in.web.response.BuyAndSellDetailResponse;
import com.example.boardservice.adapter.in.web.response.BuyAndSellListResponse;
import com.example.boardservice.application.port.in.BuyAndSellUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buy-and-sell")
public class BuyAndSellController {

    private final BuyAndSellUseCase buyAndSellUseCase;
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

        PostBuyAndSellRequest request = objectMapper.readValue(requestJson, PostBuyAndSellRequest.class);
        request.setMemberId(PassportHolder.getPassport().memberId());
        Long articleId = buyAndSellUseCase.postBuyAndSell(request.toDomain(), imageFiles);

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
        UpdateBuyAndSellRequest request = objectMapper.readValue(requestJson, UpdateBuyAndSellRequest.class);
        request.setMemberId(PassportHolder.getPassport().memberId());

        Long articleId = buyAndSellUseCase.updateBuyAndSell(request.toDomain(), imageFiles);
        return CommonResponse.okWithMessage("게시글 수정에 성공하였습니다.", articleId);
    }

    @Tag(name = "식물거래 게시글 삭제", description = "새로운 식물거래 게시글을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> deleteBuyAndSell(@PathVariable Long articleId) {
        DeleteBuyAndSellRequest request = DeleteBuyAndSellRequest.of(articleId);
        request.setMemberId(PassportHolder.getPassport().memberId());

        Long deletedArticleId = buyAndSellUseCase.delete(request.toDomain());
        return CommonResponse.okWithMessage("게시글 삭제에 성공하였습니다.", deletedArticleId);
    }

    @Tag(name = "식물거래 게시글 목록 조회", description = "식물거래 게시글 목록을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping(value = "/{page}")
    public ResponseEntity<BuyAndSellListResponse> getBuyAndSellList(@PathVariable int page) {
        BuyAndSellListResponse response = BuyAndSellListResponse.from(buyAndSellUseCase.getBuyAndSellList(page));
        return ResponseEntity.ok(response);
    }

    @Tag(name = "식물거래 게시글 상세 조회", description = "식물거래 게시글을 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<BuyAndSellDetailResponse> getBuyAndSell(@PathVariable Long id) {
        BuyAndSellDetailResponse response = BuyAndSellDetailResponse.of(buyAndSellUseCase.getBuyAndSell(id));
        return ResponseEntity.ok(response);
    }

    @Tag(name = "식물거래 게시글 작성자별 목록 조회", description = "식물거래 게시글을 목록을 작성자별로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    @GetMapping(value = "/member/{memberId}")
    public ResponseEntity<BuyAndSellListResponse> getBuyAndSellListByMemberId(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long memberId
    ) {
        BuyAndSellListResponse response = BuyAndSellListResponse.from(buyAndSellUseCase.getBuyAndSellListByMemberId(memberId, pageable));
        return ResponseEntity.ok(response);
    }
}
