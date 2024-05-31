package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.Member;
import com.example.common.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAdapter implements MemberPort {

    private final MemberJpaRepository repository;

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }

    @Override
    public void save(Member member) {
        MemberEntity memberEntity = MemberEntity.fromDomain(member);
        repository.save(memberEntity);
    }

    @Override
    @Transactional
    public void update(Member member) {
        MemberEntity memberEntity = repository.findById(member.getId())
                .orElseThrow(() -> new InternalServerException("멤버를 찾을 수 없습니다.", null));

        memberEntity.update(member);
    }

    @Override
    public Optional<Member> findByKakaoId(String kakaoId) {
        return repository.findByKakaoId(kakaoId)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByNaverId(String naverId) {
        return repository.findByNaverId(naverId)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email) {
        return repository.findByPhoneNumberAndEmail(phoneNumber, email)
                .flatMap(entity -> Optional.of(MemberEntity.toDomain(entity)));
    }
}
