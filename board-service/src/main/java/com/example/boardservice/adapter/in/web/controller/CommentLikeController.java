package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.LikeCommentCommand;
import com.example.boardservice.adapter.in.web.command.UnLikeCommentCommand;
import com.example.boardservice.application.port.in.CommentLikeUseCase;
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
@RequestMapping("/likes/comments")
@Tag(name = "댓글 좋아요", description = "댓글 좋아요 API")
public class CommentLikeController {

    private final CommentLikeUseCase likeUseCase;

    @Operation(summary = "좋아요 등록", description = "댓글에 좋아요를 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Long>> like(@PathVariable Long commentId) {
        LikeCommentCommand command = LikeCommentCommand.of(commentId, PassportHolder.getPassport().memberId());
        Long likeId = likeUseCase.like(command.toDomain());
        return CommonResponse.okWithMessage("댓글에 좋아요를 등록했습니다.", likeId);
    }

    @Operation(summary = "좋아요 해제", description = "댓글에 좋아요를 해제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Long>> unlike(@PathVariable Long commentId) {
        UnLikeCommentCommand command = UnLikeCommentCommand.of(commentId, PassportHolder.getPassport().memberId());
        Long unlikedId = likeUseCase.unlike(command.toDomain());
        return CommonResponse.okWithMessage("댓글에 좋아요를 해제했습니다.", unlikedId);
    }

}
