package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.LikeArticleCommand;
import com.example.boardservice.adapter.in.web.command.UnlikeArticleCommand;
import com.example.boardservice.adapter.in.web.response.LikeStatusResponse;
import com.example.boardservice.application.port.in.ArticleLikeUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Tag(name = "게시글 좋아요", description = "게시글 좋아요 API")
public class ArticleLikeController {

    private final ArticleLikeUseCase likeUseCase;

    @Operation(summary = "좋아요 등록", description = "게시글에 좋아요를 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> like(@PathVariable Long articleId) {
        LikeArticleCommand command = LikeArticleCommand.of(articleId, PassportHolder.getPassport().memberId());
        Long likeId = likeUseCase.like(command.toDomain());
        return CommonResponse.okWithMessage("게시글에 좋아요를 등록했습니다.", likeId);
    }

    @Operation(summary = "좋아요 해제", description = "게시글에 좋아요를 해제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> unlike(@PathVariable Long articleId) {
        UnlikeArticleCommand command = UnlikeArticleCommand.of(articleId, PassportHolder.getPassport().memberId());
        Long unlikedId = likeUseCase.unlike(command.toDomain());
        return CommonResponse.okWithMessage("게시글에 좋아요를 해제했습니다.", unlikedId);
    }

    @Operation(summary = "좋아요 상태 조회", description = "현재 회원의 게시글에 대한 좋아요 상태를 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("{articleId}")
    public ResponseEntity<LikeStatusResponse> getLikeStatusByCurrentMember(@PathVariable Long articleId) {
        LikeStatusResponse response = LikeStatusResponse.from(likeUseCase.getLikeStatusByCurrentMember(articleId, PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(response);
    }


}
