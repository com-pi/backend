package com.example.boardservice.domain;

import com.example.boardservice.exception.DuplicateLikeException;
import com.example.common.exception.UnauthorizedException;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Like {

    private Long likeId;

    @Setter
    private Long articleId;

    private Long memberId;

    private Boolean isLiked;

    /**
     * 생성자
     */
    public Like(Long likeId, Boolean isLiked) {
        this.likeId = likeId;
        this.isLiked = isLiked;
    }

    public static Like ofStatus(Long likeId, Boolean isLiked) {
        return new Like(likeId, isLiked);
    }

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

    public void validatePermission(Long originMemberId) {
        if(!Objects.equals(originMemberId, this.memberId)) {
            throw new UnauthorizedException("좋아요를 해제할 권한이 없습니다.");
        }
    }

    public void validateIfAlreadyLiked() {
        if(this.isLiked) {
            throw new DuplicateLikeException("이미 좋아요한 게시글입니다.");
        }
    }

    public void validateStatus(boolean isLiked) {
        if(!isLiked) {
            throw new DuplicateLikeException("좋아요를 해제할 상태가 아닙니다.");
        }
    }

}
