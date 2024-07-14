package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.FollowerPagingResult;
import com.example.authserver.domain.FollowingPagingResult;

public interface FollowQuery {

    FollowingPagingResult followingList(Long memberId, Integer page, Integer size);
    FollowerPagingResult followersList(Long memberId, Integer page, Integer size);

}
