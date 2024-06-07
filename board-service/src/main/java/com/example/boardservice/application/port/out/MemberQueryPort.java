package com.example.boardservice.application.port.out;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;

import java.util.Optional;

public interface MemberQueryPort {

    Optional<MemberEntity> findById(Long memberId);

}
