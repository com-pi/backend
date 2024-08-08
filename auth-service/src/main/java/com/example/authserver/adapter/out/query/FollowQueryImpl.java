package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.repository.FollowCacheRepository;
import com.example.authserver.adapter.out.repository.FollowJpaRepository;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowQueryImpl implements FollowQuery {

    private final FollowJpaRepository followJpaRepository;
    private final FollowCacheRepository followCacheRepository;

    @Override
    public Page<FollowEntity> followingList(Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followeeByMemberId = followJpaRepository.findFolloweeByMemberId(memberId, PageRequest.of(page, size));
        List<Long> followingIdList = followeeByMemberId.get().map(followEntity -> followEntity.getFollower().getId()).toList();
        followCacheRepository.recordFollow(memberId, followingIdList);
        return followeeByMemberId;
    }

    @Override
    public Page<FollowEntity> followersList(Long memberId, Integer page, Integer size) {
        Page<FollowEntity> followerByMemberId = followJpaRepository.findFollowerByMemberId(memberId, PageRequest.of(page, size));
        List<Long> followerIdList = followerByMemberId.get().map(followEntity -> followEntity.getFollowee().getId()).toList();
        followCacheRepository.recordFollow(followerIdList, memberId);
        return followerByMemberId;
    }

    @Override
    public HashMap<Long, Boolean> isFollowedByMe(Long memberId, List<Long> followeeId) {
        HashMap<Long, Boolean> result = new HashMap<>();
        List<Long> notCached = new ArrayList<>();
        for(Long id : followeeId){
            Boolean isFollowedByMe = followCacheRepository.isFollowedByMe(memberId, id);
            if(isFollowedByMe == null){
                notCached.add(id);
            } else {
                result.put(id, isFollowedByMe);
            }
        }
        for(Long id : notCached){
            Boolean isFollowedByMe = followJpaRepository.existsByFollowerIdAndFolloweeId(memberId, id);
            result.put(id, isFollowedByMe);
            if(isFollowedByMe) {
                followCacheRepository.recordFollow(memberId, id);
            } else {
                followCacheRepository.removeFollow(memberId, id);
            }
        }
        return result;
    }

}
