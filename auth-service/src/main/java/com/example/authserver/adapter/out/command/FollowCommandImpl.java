package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.repository.FollowCacheRepository;
import com.example.authserver.adapter.out.repository.FollowJpaRepository;
import com.example.authserver.application.port.out.persistence.FollowCommand;
import com.example.authserver.domain.DeleteFollow;
import com.example.authserver.domain.Follow;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowCommandImpl implements FollowCommand {

    private final FollowJpaRepository followJpaRepository;
    private final FollowCacheRepository followCacheRepository;
    private final EntityManager entityManager;

    @Override
    public void save(Follow follow) {
        FollowEntity followEntity = FollowEntity.fromDomain(follow, entityManager);
        followJpaRepository.save(followEntity);
        followCacheRepository.recordFollow(follow.follower().getId(), follow.followee().getId());
    }

    @Override
    public void delete(DeleteFollow deleteFollow) {
        followJpaRepository.deleteByFolloweeIdAnAndFollowerId(deleteFollow.followeeId(), deleteFollow.followerId());
        followCacheRepository.removeFollow(deleteFollow.followerId(), deleteFollow.followeeId());
    }

}
