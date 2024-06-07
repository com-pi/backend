package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.LikeArticleCommand;
import com.example.boardservice.adapter.in.web.command.UnlikeArticleCommand;
import com.example.boardservice.adapter.in.web.request.LikeArticleRequest;
import com.example.boardservice.adapter.in.web.request.UnlikeArticleRequest;
import com.example.boardservice.adapter.in.web.response.LikeStatusResponse;
import com.example.boardservice.application.port.in.LikeUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeUseCase likeUseCase;

    @Tag(name = "게시글 찜", description = "게시글을 찜합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> like(
            @Schema(
                    example = "{\"articleId\":1}"
            )
            @RequestBody LikeArticleRequest request) {
        LikeArticleCommand command = LikeArticleCommand.of(request.articleId(), PassportHolder.getPassport().memberId());
        Long likeId = likeUseCase.like(command.toDomain());
        return CommonResponse.okWithMessage("게시글에 찜을 등록했습니다.", likeId);
    }

    @Tag(name = "게시글 찜 해제", description = "게시글 찜을 해제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping
    public ResponseEntity<CommonResponse<Long>> unlike(
            @Schema(
                    example = "{\"likeId\":1}"
            )
            @RequestBody UnlikeArticleRequest request) {
        UnlikeArticleCommand command = UnlikeArticleCommand.of(request.likeId(), PassportHolder.getPassport().memberId());
        Long likeId = likeUseCase.unlike(command.toDomain());
        return CommonResponse.okWithMessage("게시글 찜을 해제했습니다.", likeId);
    }

    @Tag(name = "게시글 찜 조회", description = "현재 회원의 찜 상태를 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("{articleId}")
    public ResponseEntity<LikeStatusResponse> getLikeStatusByCurrentMember(@PathVariable Long articleId) {
        LikeStatusResponse response = LikeStatusResponse.from(likeUseCase.getLikeStatusByCurrentMember(articleId, PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(response);
    }
}
