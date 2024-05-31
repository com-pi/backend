package com.example.authserver.application.port.out.persistence;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.adapter.out.MemberJpaRepository;
import com.example.common.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
class MemberEntityPortTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    GeometryFactory geometryFactory = new GeometryFactory();

    @BeforeEach
    void init(){
        MemberEntity test_memberEntity = MemberEntity.builder()
                .email("kihong@google.com")
                .nickname("테스트 회원")
                .phoneNumber("01094862225")
                .location(geometryFactory.createPoint(new Coordinate(127.01, 38.04)))
                .role(Role.MEMBER)
                .build();

        memberJpaRepository.saveAndFlush(test_memberEntity);
    }

    @Test
    @DisplayName("지리데이터 핸들링 테스트")
    void getMember(){
        Optional<MemberEntity> found = memberJpaRepository.findByPhoneNumberAndEmailAndDeletionYn(
                "01094862225", "kihong@google.com", "N");

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(127.01, found.get().getLocation().getCoordinate().getX());
    }


}