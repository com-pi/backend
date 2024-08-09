package com.example.authserver.application.port.out.persistence;

import com.example.authserver.adapter.out.entity.FollowEntity;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface FollowQuery {

    Integer countFollowee(Long memberId);

    Integer countFollower(Long memberId);

    Page<FollowEntity> followingList(Long memberId, Integer page, Integer size);
    Page<FollowEntity> followersList(Long memberId, Integer page, Integer size);
    HashMap<Long, Boolean> isFollowedByMember(Long memberId, List<Long> followeeId);

    Boolean isFollowedByMember(Long memberId, Long followeeId);
}
