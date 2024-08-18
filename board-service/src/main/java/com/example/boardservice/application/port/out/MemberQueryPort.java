package com.example.boardservice.application.port.out;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.domain.MemberBriefInfoList;

import java.util.List;
import java.util.Optional;

public interface MemberQueryPort {

    Optional<MemberEntity> findById(Long memberId);

    MemberBriefInfoList getMemberList(List<Long> memberIdlist);
}
