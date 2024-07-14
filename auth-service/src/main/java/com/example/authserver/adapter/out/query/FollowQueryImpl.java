package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.repository.FollowJpaRepository;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import com.example.authserver.domain.FollowerPagingResult;
import com.example.authserver.domain.FollowingPagingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowQueryImpl implements FollowQuery {

    private final FollowJpaRepository followJpaRepository;

    @Override
    public FollowingPagingResult followingList(Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followeeList = followJpaRepository
                .findFolloweeByMemberId(memberId, PageRequest.of(page, size));
        return FollowingPagingResult.of(followeeList);
    }

    @Override
    public FollowerPagingResult followersList(Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followerList = followJpaRepository
                .findFollowerByMemberId(memberId, PageRequest.of(page, size));
        return FollowerPagingResult.of(followerList);
    }

}
