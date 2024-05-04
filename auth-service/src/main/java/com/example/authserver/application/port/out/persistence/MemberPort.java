package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPort extends JpaRepository<Member, Long> {

    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByNaverId(String naverId);

}
