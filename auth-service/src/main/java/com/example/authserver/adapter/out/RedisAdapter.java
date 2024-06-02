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
    private final String VERIFICATION_CODE_REDIS_KEY = "VerificationCode:";
    private final String FIND_ID_CODE_REDIS_KEY = "FindId:";
    private final String FIND_PASSWORD_REDIS_KEY = "FindPassword:";
    private final String CHANGE_PASSWORD_REDIS_KEY = "ChangePassword:";

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

    @Override
    public void saveVerificationCode(String email, String verificationCode) {
        redisTemplate.opsForValue().set(
                VERIFICATION_CODE_REDIS_KEY + email,
                verificationCode,
                Duration.ofSeconds(180));
    }

    @Override
    public Optional<String> getVerificationCode(String phoneNumber) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(VERIFICATION_CODE_REDIS_KEY + phoneNumber)
        );
    }

    @Override
    public void verifyNumber(String phoneNumber, String email) {
        redisTemplate.opsForValue().set(
                VERIFICATION_CODE_REDIS_KEY + phoneNumber,
                email,
                Duration.ofHours(1));
    }

    @Override
    public boolean checkVerification(String phoneNumber, String email) {
        return email.equals(
                redisTemplate.opsForValue().get(VERIFICATION_CODE_REDIS_KEY + phoneNumber));
    }

    @Override
    public void setFindIdValidationCode(String phoneNumber, String verificationCode) {
        redisTemplate.opsForValue().set(FIND_ID_CODE_REDIS_KEY + phoneNumber, verificationCode);
    }

    @Override
    public boolean verifyFindIdCode(String phoneNumber, String verificationCode) {
        return verificationCode.equals(
                redisTemplate.opsForValue().get(FIND_ID_CODE_REDIS_KEY + phoneNumber));
    }

    @Override
    public void setFindPasswordValidationCode(String phoneNumber, String email, String verificationCode) {
        redisTemplate.opsForValue().set(FIND_PASSWORD_REDIS_KEY + String.format("%s/%s", phoneNumber, email),
                verificationCode,
                Duration.ofMinutes(3));
    }

    @Override
    public boolean verifyFindPasswordValidationCode(String phoneNumber, String email, String givenCode) {
        return givenCode.equals(
                redisTemplate.opsForValue().get(FIND_PASSWORD_REDIS_KEY + String.format("%s/%s", phoneNumber, email)));
    }

    @Override
    public void setChangePasswordCode(String phoneNumber, String email, ComPToken comPToken) {
        redisTemplate.opsForValue().set(
                CHANGE_PASSWORD_REDIS_KEY + String.format("%s/%s", phoneNumber, email),
                comPToken.getToken(),
                Duration.ofMinutes(5));
    }

    @Override
    public boolean verifyChangePasswordToken(String phoneNumber, String email, String code) {
        boolean isMatch = code.equals(
                redisTemplate.opsForValue()
                        .get(CHANGE_PASSWORD_REDIS_KEY + String.format("%s/%s", phoneNumber, email))
        );
        redisTemplate.delete(CHANGE_PASSWORD_REDIS_KEY + String.format("%s/%s", phoneNumber, email));
        return isMatch;
    }
}
