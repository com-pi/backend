package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.adapter.out.repository.MemberJpaRepository;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Primary
public class JpaMemberQueryImpl implements MemberQuery {

    private final MemberJpaRepository memberRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Override
    public Optional<Member> findByKakaoId(String kakaoId) {
    return memberRepository.findByKakaoId(kakaoId)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Override
    public Optional<Member> findByNaverId(String naverId) {
        return memberRepository.findByNaverId(naverId)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Override
    public Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email) {
        return memberRepository.findByPhoneNumberAndEmail(phoneNumber, email)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }
}
