package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.common.domain.Passport;

import java.util.Optional;

public interface RedisPort {

    void saveRefreshToken(Member member, ComPToken refreshToken);
    Optional<String> getRefreshToken(Passport passport);
    void removeRefreshToken(Passport passport);

    void saveVerificationCode(String email, String verificationCode);
    Optional<String> getVerificationCode(String phoneNumber);
    void verifyNumber(String phoneNumber, String email);
    boolean checkVerification(String phoneNumber, String email);

}
