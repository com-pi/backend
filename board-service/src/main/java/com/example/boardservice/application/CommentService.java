package com.example.boardservice.application;

import com.example.boardservice.application.port.in.CommentUseCase;
import com.example.boardservice.application.port.out.CommentCommandPort;
import com.example.boardservice.application.port.out.CommentQueryPort;
import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.CommentWithReplies;
import com.example.boardservice.security.PassportHolder;
import com.example.common.exception.NotFoundException;
import com.example.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase {

    private final CommentCommandPort commentCommandPort;
    private final CommentQueryPort commentQueryPort;

    @Override
    @Transactional
    public Long post(Comment comment) {
        Comment savedComment = commentCommandPort.save(comment);
        return savedComment.getCommentId();
    }

    @Override
    public Long postReply(Comment comment) {
        canPostReply(comment.getParent().getCommentId());
        Comment savedComment = commentCommandPort.saveReply(comment);
        return savedComment.getCommentId();
    }

    @Override
    @Transactional
    public Long update(Comment comment) {
        Comment originComment = commentQueryPort.getComment(comment.getCommentId());
        validatePermission(originComment.getMemberId(), comment.getMemberId());

        commentCommandPort.update(comment);
        return comment.getCommentId();
    }

    @Override
    @Transactional
    public Long delete(Comment comment) {
        Comment originComment = commentQueryPort.getComment(comment.getCommentId());
        validatePermission(originComment.getMemberId(), comment.getMemberId());

        commentCommandPort.delete(comment);
        return comment.getCommentId();
    }

    @Override
    public List<CommentWithReplies> getCommentList(Long articleId) {
        List<Comment> commentList = commentQueryPort.getCommentList(articleId);
        checkEditable(commentList);

        List<Comment> parentComments = getParents(commentList);
        List<CommentWithReplies> commentWithRepliesList = parentComments.stream()
                .map(parentComment -> {
                    List<Comment> children = getChildren(commentList, parentComment.getCommentId());
                    return CommentWithReplies.of(parentComment, children);
                })
                .toList();
        return commentWithRepliesList;
    }

    /**
     * private
     */
    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("댓글을 수정하거나 삭제할 권한이 없습니다.");
        }
    }

    private void canPostReply(Long commentId) {
        try {
            commentQueryPort.getComment(commentId);
        } catch (NotFoundException e) {
            throw new UnauthorizedException("답글 댓글을 작성할 수 없는 부모 댓글입니다.");
        }
    }

    private List<Comment> getParents(List<Comment> commentList) {
        return commentList.stream()
                .filter(comment -> comment.getParent() == null)
                .toList();
    }

    private List<Comment> getChildren(List<Comment> commentList, Long parentId) {
        return commentList.stream()
                .filter(comment -> comment.getParent() != null && comment.getParent().getCommentId().equals(parentId))
                .toList();
    }

    private void checkEditable(List<Comment> commentList) {
        commentList.forEach(comment -> comment.addEditable(PassportHolder.getPassport().memberId()));
    }

}
