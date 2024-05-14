package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPort extends JpaRepository<Member, Long> {
}
