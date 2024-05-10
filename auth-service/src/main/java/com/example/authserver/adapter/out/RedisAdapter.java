package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.Member;
import com.example.common.domain.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisAdapter implements RedisPort {

    private final RedisTemplate<String, String> redisTemplate;
    // Todo : 상수 관리 ...
    private final Integer REFRESH_TOKEN_EXPIRE_DAYS = 14;

    @Override
    public void saveRefreshToken(Member member, String refreshToken) {
        redisTemplate.opsForValue().set(
                String.format("RefreshToken:%s", member.getId()),
                refreshToken,
                Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAYS));
    }

    @Override
    public Optional<String> getRefreshToken(Passport passport) {
            return Optional.ofNullable(redisTemplate.opsForValue()
                    .get(String.format("RefreshToken:%s", passport.memberId())));
    }

}
