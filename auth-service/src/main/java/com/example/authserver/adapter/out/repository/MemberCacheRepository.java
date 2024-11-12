package com.example.authserver.adapter.out.repository;

import com.example.authserver.domain.MemberBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final RedisTemplate<String, MemberBrief> redisTemplate;
    private static final String MEMBER_BRIEF_KEY = "memberBrief:";

    @Async
    public void saveMemberBrief(MemberBrief memberBrief) {
        long randomExpireTimeInSeconds = ThreadLocalRandom.current().nextLong(7200, 14401);  // 2시간 ~ 4시간
        redisTemplate.opsForValue().set(MEMBER_BRIEF_KEY + memberBrief.id(), memberBrief, Duration.ofSeconds(randomExpireTimeInSeconds));
    }

    public MemberBrief getMemberBrief(Long id) {
        return redisTemplate.opsForValue().get(MEMBER_BRIEF_KEY + id);
    }

    @Async
    public void deleteMemberBrief(Long id) {
        redisTemplate.delete(MEMBER_BRIEF_KEY + id);
    }

}
