package com.example.boardservice.domain;

import com.example.boardservice.exception.DuplicateLikeException;
import com.example.common.exception.UnauthorizedException;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CommentLike {

    private Long likeId;

    private Long commentId;

    private Long memberId;

    private Boolean isLiked;

    public void like() {
        this.isLiked = true;
    }

    public void unlike() {
        this.isLiked = false;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public void updateLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public void validateIfAlreadyLiked() {
        if(this.isLiked) {
            throw new DuplicateLikeException("이미 좋아요한 댓글입니다.");
        }
    }

    public void validatePermission(Long originMemberId) {
        if(!Objects.equals(originMemberId, this.memberId)) {
            throw new UnauthorizedException("좋아요를 해제할 권한이 없습니다.");
        }
    }

    public void validateStatus(boolean isLiked) {
        if(!isLiked) {
            throw new DuplicateLikeException("좋아요를 해제할 상태가 아닙니다.");
        }
    }
}
