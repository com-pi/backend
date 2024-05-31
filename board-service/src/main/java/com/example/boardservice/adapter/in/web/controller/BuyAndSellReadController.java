package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.response.BuyAndSellListResponse;
import com.example.boardservice.application.port.in.ReadArticleUseCase;
import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuyAndSellReadController {

    private final ReadArticleUseCase readArticleUseCase;

    @Tag(name = "식물거래 게시글 목록 조회", description = "새로운 식물거래 게시글 목록을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping(value = "/buy-and-sell/{page}")
    public ResponseEntity<BuyAndSellListResponse> getBuyAndSellArticleList(@PathVariable int page) {
        BuyAndSellListResponse response = readArticleUseCase.getBuyAndSellList(page);
        return ResponseEntity.ok(response);
    }
}
