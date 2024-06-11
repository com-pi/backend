package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
