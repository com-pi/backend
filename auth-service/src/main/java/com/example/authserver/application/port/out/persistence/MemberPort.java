package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberPort extends JpaRepository<Member, Long> {

    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByNaverId(String naverId);


    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletionYn = :isDeleted")
    Optional<Member> findByEmailAndDeletionYn(
            @Param("email") String email, @Param("isDeleted") String deletionYn);

    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletionYn = 'Y'")
    Optional<Member> findByEmail(@Param("email") String email);
}
