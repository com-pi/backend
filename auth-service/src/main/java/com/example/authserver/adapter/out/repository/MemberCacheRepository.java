package com.example.authserver.adapter.out.repository;

import com.example.authserver.domain.MemberBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final RedisTemplate<String, MemberBrief> redisTemplate;
    private static final String MEMBER_BRIEF_KEY = "memberBrief:";

    // Todo: 회원 정보 변경시 캐시 업데이트 로직 필요

    @Async
    public void saveMemberBrief(MemberBrief memberBrief) {
        redisTemplate.opsForValue().set(MEMBER_BRIEF_KEY + memberBrief.id(), memberBrief);
    }

    public MemberBrief getMemberBrief(Long id) {
        return redisTemplate.opsForValue().get(MEMBER_BRIEF_KEY + id);
    }

}
