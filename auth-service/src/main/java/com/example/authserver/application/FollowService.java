package com.example.authserver.application;

import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.FollowUseCase;
import com.example.authserver.application.port.out.persistence.FollowCommand;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.*;
import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public FollowerPagingResult getFollowerList(Long memberId, Integer page, Integer size) {
        return followQuery.followersList(memberId, page, size);
    }

    @Override
    public FollowingPagingResult getFollowingList(Long memberId, Integer page, Integer size) {
        return followQuery.followingList(memberId, page, size);
    }

}
