package com.example.authserver.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String FOLLOWER_KEY = "follower:";

    @Async
    public void recordFollow(Long followerId, List<Long> followeeId) {
        followeeId.forEach(id -> recordFollow(followerId, id));
    }

    @Async
    public void recordFollow(List<Long> followerId, Long followeeId) {
        followerId.forEach(id -> recordFollow(id, followeeId));
    }

    @Async
    public void recordFollow(Long followerId, Long followeeId) {
        redisTemplate.opsForHash().put(FOLLOWER_KEY + followerId, String.valueOf(followeeId), "1");
    }

    @Async
    public void removeFollow(Long followerId, Long followeeId) {
        redisTemplate.opsForHash().put(FOLLOWER_KEY + followerId, String.valueOf(followeeId), "0");
    }

    @Nullable
    public Boolean isFollowedByMe(Long followerId, Long followeeId) {
        Object value = redisTemplate.opsForHash().get(FOLLOWER_KEY + followerId, String.valueOf(followeeId));
        if (value == null) {
            return null;
        }
        return "1".equals(value);
    }

}
