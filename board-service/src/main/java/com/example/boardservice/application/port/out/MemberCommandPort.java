package com.example.boardservice.application.port.out;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;

public interface MemberCommandPort {

    void saveAndFlush(MemberEntity member);

    void save(MemberEntity member);
}
