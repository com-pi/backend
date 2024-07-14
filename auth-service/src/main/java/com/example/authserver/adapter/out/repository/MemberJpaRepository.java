package com.example.authserver.adapter.out.repository;

import com.example.authserver.adapter.out.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByKakaoId(String kakaoId);
    Optional<MemberEntity> findByNaverId(String kakaoId);
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByPhoneNumber(String phoneNumber);
    Optional<MemberEntity> findByPhoneNumberAndEmail(String phoneNumber, String email);

}
