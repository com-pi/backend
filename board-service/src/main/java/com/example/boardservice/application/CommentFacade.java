package com.example.boardservice.application;

import com.example.boardservice.application.port.in.CommentUseCase;
import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.CommentWithReplies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade implements CommentUseCase {

    private final CommentService commentService;
    private final CommentLikeService likeService;

    @Override
    public Long post(Comment comment) {
        return commentService.post(comment);
    }

    @Override
    public Long postReply(Comment comment) {
        return commentService.postReply(comment);
    }

    @Override
    public Long update(Comment comment) {
        return commentService.update(comment);
    }

    @Override
    public Long delete(Comment comment) {
        return commentService.delete(comment);
    }

    @Override
    public List<CommentWithReplies> getCommentList(Comment comment) {
        List<CommentWithReplies> commentList = commentService.getCommentList(comment.getArticleId());
        addLikeStatusList(commentList, comment.getMemberId());
        return commentList;
    }

    /**
     * private
     */
    private void addLikeStatusList(List<CommentWithReplies> commentList, Long memberId) {
        // 부모
        List<Long> parentCommentIdlist = CommentWithReplies.getParentIdList(commentList);
        List<Boolean> parentLikeStatus = likeService.getLikeStatusByCommentList(parentCommentIdlist, memberId);
        for (int i = 0; i < commentList.size(); i++) {
            commentList.get(i).getComment().addLikeStatus(parentLikeStatus.get(i));
        }

        // 자식
        List<Long> childCommentIdList = CommentWithReplies.getChildIdList(commentList);
        List<Boolean> childLikeStatus = likeService.getLikeStatusByCommentList(childCommentIdList, memberId);
        int childIndex = 0;
        for (CommentWithReplies commentWithReplies : commentList) {
            for (Comment childComment : commentWithReplies.getChildren()) {
                childComment.addLikeStatus(childLikeStatus.get(childIndex++));
            }
        }
    }
}
