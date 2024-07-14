package com.example.authserver.application.port.in;

import com.example.authserver.domain.FollowerPagingResult;
import com.example.authserver.domain.FollowingPagingResult;

public interface FollowUseCase {

    void follow(Long followeeId);
    void unFollow(Long followeeId);

    FollowerPagingResult getFollowerList(Long memberId, Integer page, Integer size);
    FollowingPagingResult getFollowingList(Long memberId, Integer page, Integer size);

}
