package com.example.authserver.application;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.FollowUseCase;
import com.example.authserver.application.port.out.persistence.FollowCommand;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.*;
import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class FollowService implements FollowUseCase {

    private final FollowCommand followCommand;
    private final FollowQuery followQuery;
    private final MemberQuery memberQuery;

    @Override
    @Transactional
    public void follow(Long followeeId) {
        Passport passport = PassportHolder.getPassport();
        if(followeeId.equals(passport.memberId())){
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        Member followee = memberQuery.findById(followeeId).orElseThrow(() -> new NotFoundException(Member.class));
        Follow follow = Follow.builder()
                .follower(Member.ofId(passport.memberId()))
                .followee(followee)
                .build();
        followCommand.save(follow);
    }

    @Override
    @Transactional
    public void unFollow(Long followeeId) {
        Passport passport = PassportHolder.getPassport();
        DeleteFollow deleteFollow = DeleteFollow.builder()
                .followerId(passport.memberId())
                .followeeId(followeeId)
                .build();
        followCommand.delete(deleteFollow);
    }

    @Override
    public FollowerPagingResult getFollowerList(Long myId, Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followEntities = followQuery.followersList(memberId, page, size);
        HashMap<Long, Boolean> followedByMe = followQuery.isFollowedByMember(myId,
                followEntities.get().map(followEntity -> followEntity.getFollower().getId()).toList());
        return FollowerPagingResult.builder()
                .totalPage(followEntities.getTotalPages())
                .totalElement(followEntities.getTotalElements())
                .followerMemberList(followEntities.get().map(followEntity -> {
                    MemberEntity follower = followEntity.getFollower();
                    return FollowMember.builder()
                            .id(follower.getId())
                            .nickname(follower.getNickname())
                            .thumbnailUrl(follower.getThumbnailUrl())
                            .imageUrl(follower.getImageUrl())
                            .isFollowed(followedByMe.get(follower.getId()))
                            .build();
                }).toList()).build();
    }

    @Override
    public FollowingPagingResult getFollowingList(Long myId, Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followEntities = followQuery.followingList(memberId, page, size);
        HashMap<Long, Boolean> followedByMe = followQuery.isFollowedByMember(myId,
                followEntities.get().map(followEntity -> followEntity.getFollowee().getId()).toList());
        return FollowingPagingResult.builder()
                .totalPage(followEntities.getTotalPages())
                .totalElement(followEntities.getTotalElements())
                .followingMemberList(followEntities.get().map(followEntity -> {
                    MemberEntity followee = followEntity.getFollowee();
                    return FollowMember.builder()
                            .id(followee.getId())
                            .nickname(followee.getNickname())
                            .thumbnailUrl(followee.getThumbnailUrl())
                            .imageUrl(followee.getImageUrl())
                            .isFollowed(followedByMe.get(followee.getId()))
                            .build();
                }).toList()).build();
    }

}
