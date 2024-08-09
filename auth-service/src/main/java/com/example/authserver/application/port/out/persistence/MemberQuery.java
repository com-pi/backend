package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;
import com.example.authserver.domain.MemberBrief;

import java.util.List;
import java.util.Optional;

public interface MemberQuery {
    Optional<Member> findById(Long id);
    MemberBrief findBriefById(Long id);
    List<MemberBrief> findAllBriefById(List<Long> ids);
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByNaverId(String naverId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhoneNumber(String phoneNumber);
    Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email);
}
