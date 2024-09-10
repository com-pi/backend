package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.ComppiToken;
import com.example.authserver.domain.Member;
import com.example.common.domain.Passport;

import java.util.Optional;

public interface RedisPort {

    // Todo : 메서드명 너무 복잡함 리펙토링 필요

    // 리프레시 토큰 관련
    void saveRefreshToken(Member member, ComppiToken refreshToken);
    Optional<String> getRefreshToken(Passport passport);
    void removeRefreshToken(Passport passport);

    // 회원가입 관련
    void saveVerificationCode(String email, String verificationCode);
    Optional<String> getVerificationCode(String phoneNumber);
    void verifyNumber(String phoneNumber, String email);
    boolean checkVerification(String phoneNumber, String email);

    // 가입 이메일 찾기 관련 
    void setFindIdValidationCode(String phoneNumber, String verificationCode);
    boolean verifyFindIdCode(String phoneNumber, String verificationCode);

    // 비밀번호 찾기 관련
    void setFindPasswordValidationCode(String phoneNumber, String email, String verificationCode);
    boolean verifyFindPasswordValidationCode(String phoneNumber, String email, String givenCode);
    void setChangePasswordCode(String phoneNumber, String email, ComppiToken comppiToken);
    boolean verifyChangePasswordToken(String phoneNumber, String email, String code);

}
