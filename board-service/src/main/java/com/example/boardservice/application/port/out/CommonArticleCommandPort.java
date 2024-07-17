package com.example.boardservice.application.port.out;

public interface CommonArticleCommandPort {
    void increaseLikeCount(Long articleId);

    void decreaseLikeCount(Long articleId);

    void increaseCommentCount(Long articleId);

    void decreaseCommentCount(Long articleId);
}
