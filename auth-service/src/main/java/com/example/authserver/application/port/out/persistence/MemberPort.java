package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;

import java.util.Optional;

public interface MemberPort {

    void save(Member member);
    void update(Member member);

    Optional<Member> findById(Long id);
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByNaverId(String naverId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhoneNumber(String phoneNumber);
    Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email);
}
