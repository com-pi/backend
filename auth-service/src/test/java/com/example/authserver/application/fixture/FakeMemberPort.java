package com.example.authserver.application.fixture;

import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.Member;

import java.util.Optional;

public class FakeMemberPort implements MemberPort {
    @Override
    public void save(Member member) {

    }

    @Override
    public void update(Member member) {

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByKakaoId(String kakaoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByNaverId(String naverId) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email) {
        return Optional.empty();
    }
}
