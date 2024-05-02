package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
