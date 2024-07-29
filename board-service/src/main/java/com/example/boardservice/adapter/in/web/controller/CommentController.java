package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.DeleteCommentCommand;
import com.example.boardservice.adapter.in.web.command.PostCommentCommand;
import com.example.boardservice.adapter.in.web.command.PostReplyCommand;
import com.example.boardservice.adapter.in.web.command.UpdateCommentCommand;
import com.example.boardservice.adapter.in.web.request.PostCommentRequest;
import com.example.boardservice.adapter.in.web.request.PostReplyRequest;
import com.example.boardservice.adapter.in.web.request.UpdateCommentRequest;
import com.example.boardservice.adapter.in.web.response.CommentWithRepliesResponse;
import com.example.boardservice.application.port.in.CommentUseCase;
import com.example.boardservice.domain.CommentWithReplies;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Tag(name = "게시글 댓글", description = "게시글 댓글 API")
public class CommentController {

    private final CommentUseCase commentUseCase;

    @Operation(summary = "일반 댓글 등록", description = "일반 댓글을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> post(
            @Schema(example = "{\"content\":\"새로운 댓글을 등록합니다.\",\"articleId\":1}")
            @RequestBody PostCommentRequest request) {
        Long commentId = commentUseCase.post(PostCommentCommand.of(request, PassportHolder.getPassport().memberId()).toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일반 댓글이 등록되었습니다.", commentId));
    }

    @Operation(summary = "답글 댓글 등록", description = "답글 댓글을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping("/replies")
    public ResponseEntity<CommonResponse<Long>> postReply(
            @Schema(
                    example = "{"
                            + "\"content\":\"답글 댓글입니다.\","
                            + "\"parentId\": 1"
                            + "}"
            )
            @RequestBody PostReplyRequest request) {
        Long commentId = commentUseCase.postReply(PostReplyCommand.of(request, PassportHolder.getPassport().memberId()).toDomain());
        return ResponseEntity.ok(new CommonResponse<>("답글 댓글이 등록되었습니다.", commentId));
    }


    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Long>> update(
            @Schema(
                    example = "{"
                            + "\"content\": \"수정 댓글입니다.\""
                            + "}"
            )
            @RequestBody UpdateCommentRequest request,
            @PathVariable Long commentId
    ) {
        Long updatedCommentId = commentUseCase.update(UpdateCommentCommand.of(request, commentId, PassportHolder.getPassport().memberId()).toDomain());
        return ResponseEntity.ok(new CommonResponse<>("댓글이 수정되었습니다.", updatedCommentId));
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Long>> update(@PathVariable Long commentId) {
        Long deletedCommentId = commentUseCase.delete(DeleteCommentCommand.of(PassportHolder.getPassport().memberId(), commentId).toDomain());
        return ResponseEntity.ok(new CommonResponse<>("댓글이 삭제되었습니다.", deletedCommentId));
    }

    @Operation(summary = "댓글, 답글 목록 조회", description = "댓글과 답글을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/{articleId}")
    @Parameters({
            @Parameter(name = "parent-page", description = "부모 페이지 번호", example = "0"),
            @Parameter(name = "parent-size", description = "부모 페이지 크기", example = "4"),
            @Parameter(name = "children-page", description = "자식 페이지 번호", example = "0"),
            @Parameter(name = "children-size", description = "자식 페이지 크기", example = "4"),
    })
    public ResponseEntity<List<CommentWithRepliesResponse>> getCommentList(
            @RequestParam("parent-page") int parentPage,
            @RequestParam("parent-size") int parentSize,
            @RequestParam("children-page") int childrenPage,
            @RequestParam("children-size") int childrenSize,
            @PathVariable final Long articleId
        ) {
        List<CommentWithReplies> commentWithRepliesList = commentUseCase.getCommentList(articleId);
        return ResponseEntity.ok(commentWithRepliesList.stream().map(CommentWithRepliesResponse::from).toList());
    }
}
