package com.example.authserver.adapter.out.query;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberMongoRepository extends MongoRepository<MemberDocument, String> {

    Optional<MemberDocument> findByEmail(String email);
    Optional<MemberDocument> findByKakaoId(String kakaoId);
    Optional<MemberDocument> findByNaverId(String naverId);
    Optional<MemberDocument> findByPhoneNumber(String phoneNumber);
    Optional<MemberDocument> findByPhoneNumberAndEmail(String phoneNumber, String email);

}
