package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
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
    private final String REFRESH_TOKEN_REDIS_KEY = "RefreshToken:";

    @Override
    public void saveRefreshToken(Member member, ComPToken refreshToken) {
        redisTemplate.opsForValue().set(
                String.format(REFRESH_TOKEN_REDIS_KEY + member.getId()),
                refreshToken.getToken(),
                Duration.ofSeconds(refreshToken.getTokenType().getSeconds()));
    }

    @Override
    public Optional<String> getRefreshToken(Passport passport) {
            return Optional.ofNullable(redisTemplate.opsForValue()
                    .get(String.format(REFRESH_TOKEN_REDIS_KEY + passport.memberId())));
    }

    @Override
    public void removeRefreshToken(Passport passport) {
        redisTemplate.delete(REFRESH_TOKEN_REDIS_KEY + passport.memberId());
    }

}
