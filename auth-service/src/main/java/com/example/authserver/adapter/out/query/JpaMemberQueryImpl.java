package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.repository.MemberCacheRepository;
import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.adapter.out.repository.MemberJpaRepository;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.MemberBrief;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Primary
public class JpaMemberQueryImpl implements MemberQuery {

    private final MemberJpaRepository memberRepository;
    private final MemberCacheRepository memberCacheRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id)
                .flatMap(memberEntity -> Optional.ofNullable(MemberEntity.toDomain(memberEntity)));
    }

    @Nullable
    @Override
    public MemberBrief findBriefById(Long id) {
        return memberCacheRepository.getMemberBrief(id);
    }

    @Override
    public List<MemberBrief> findAllBriefById(List<Long> ids) {
        List<MemberEntity> memberEntityList = memberRepository.findAllById(ids);
        return memberEntityList.stream().map(
                memberEntity -> {
                    MemberBrief memberBrief = MemberEntity.toBrief(memberEntity);
                    memberCacheRepository.saveMemberBrief(memberBrief);
                    return memberBrief;
                }
        ).toList();
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
