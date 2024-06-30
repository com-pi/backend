package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentEntity;
import com.example.boardservice.application.port.out.CommentCommandPort;
import com.example.boardservice.domain.Comment;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentCommandAdapter implements CommentCommandPort {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = commentRepository.save(CommentEntity.fromDomain(comment));
        return commentEntity.toDomain();
    }

    @Override
    @Transactional
    public Comment saveReply(Comment comment) {
        CommentEntity parentEntity = commentRepository.findById(comment.getParent().getCommentId())
                .orElseThrow(() -> new NotFoundException(CommentEntity.class));
        CommentEntity childEntity = CommentEntity.fromDomain(comment);
        childEntity.addParent(parentEntity);
        childEntity.addArticle(parentEntity.getArticle().getArticleId());
        CommentEntity savedChildEntity = commentRepository.save(childEntity);

        return savedChildEntity.toDomain();
    }

    @Override
    public void update(Comment comment) {
        CommentEntity commentEntity = commentRepository.findById(comment.getCommentId())
                        .orElseThrow(() -> new NotFoundException(CommentEntity.class));
        commentEntity.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        CommentEntity commentEntity = commentRepository.findById(comment.getCommentId())
                .orElseThrow(() -> new NotFoundException(CommentEntity.class));
        commentEntity.delete();
    }
}
