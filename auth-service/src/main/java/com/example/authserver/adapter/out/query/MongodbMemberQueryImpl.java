package com.example.authserver.adapter.out.query;

import com.example.authserver.adapter.out.entity.MemberDocument;
import com.example.authserver.adapter.out.repository.MemberMongoRepository;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongodbMemberQueryImpl implements MemberQuery {

    private final MemberMongoRepository repository;

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id.toString())
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByKakaoId(String kakaoId) {
        return repository.findByKakaoId(kakaoId)
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByNaverId(String naverId) {
        return repository.findByNaverId(naverId)
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email)
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }

    @Override
    public Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email) {
        return repository.findByPhoneNumberAndEmail(phoneNumber, email)
                .flatMap(entity -> Optional.of(MemberDocument.toDomain(entity)));
    }
}
