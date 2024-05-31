package com.example.authserver.application.fixture;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
import com.example.common.domain.Passport;

import java.util.Optional;

public class FakeRedisPort implements RedisPort {


    @Override
    public void saveRefreshToken(MemberEntity memberEntity, ComPToken refreshToken) {
    }

    @Override
    public Optional<String> getRefreshToken(Passport passport) {
        return Optional.empty();
    }

    @Override
    public void removeRefreshToken(Passport passport) {
    }

    @Override
    public void saveVerificationCode(String email, String verificationCode) {
    }

    @Override
    public Optional<String> getVerificationCode(String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public void verifyNumber(String phoneNumber, String email) {

    }

    @Override
    public boolean checkVerification(String phoneNumber, String email) {
        return false;
    }

    @Override
    public void setFindIdValidationCode(String phoneNumber, String verificationCode) {

    }

    @Override
    public boolean verifyFindIdCode(String phoneNumber, String verificationCode) {
        return false;
    }

    @Override
    public void setFindPasswordValidationCode(String phoneNumber, String email, String verificationCode) {

    }

    @Override
    public boolean verifyFindPasswordValidationCode(String phoneNumber, String email, String givenCode) {
        return false;
    }

    @Override
    public void setChangePasswordCode(String phoneNumber, String email, ComPToken comPToken) {

    }

    @Override
    public boolean verifyChangePasswordToken(String phoneNumber, String email, String code) {
        return false;
    }
}
