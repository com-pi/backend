package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.repository.FollowCacheRepository;
import com.example.authserver.adapter.out.repository.FollowJpaRepository;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowQueryImpl implements FollowQuery {

    private final FollowJpaRepository followJpaRepository;
    private final FollowCacheRepository followCacheRepository;

    @Override
    public Integer countFollowee(Long memberId) {
        return followJpaRepository.countByFollowerId(memberId);
    }

    @Override
    public Integer countFollower(Long memberId) {
        return followJpaRepository.countByFolloweeId(memberId);
    }

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
    public HashMap<Long, Boolean> isFollowedByMember(Long memberId, List<Long> followeeIds) {
        HashMap<Long, Boolean> result = new HashMap<>();
        for (Long id : followeeIds) {
            Boolean isFollowed = isFollowedByMember(memberId, id);
            result.put(id, isFollowed);
        }
        return result;
    }

    @Override
    public Boolean isFollowedByMember(Long memberId, Long followeeId) {
        Boolean isFollowedByMe = followCacheRepository.isFollowedByMe(memberId, followeeId);
        if(isFollowedByMe != null){
            return isFollowedByMe;
        }
        Boolean isFollowed = followJpaRepository.existsByFollowerIdAndFolloweeId(memberId, followeeId);
        if(isFollowed){
            followCacheRepository.recordFollow(memberId, followeeId);
        } else {
            followCacheRepository.removeFollow(memberId, followeeId);
        }
        return isFollowed;
    }

}
