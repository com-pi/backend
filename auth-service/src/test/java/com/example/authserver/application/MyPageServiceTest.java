package com.example.authserver.application;

import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.imagemodule.application.port.ImageCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class MyPageServiceTest {

    MemberService memberService;
    MemberCommand memberCommand;
    MemberQuery memberQuery;
    ImageCommand imageCommand;
    AddressConverterPort addressConverter;

    @BeforeEach
    void setUp() {
        memberCommand = mock(MemberCommand.class);
        memberQuery = mock(MemberQuery.class);
        imageCommand = mock(ImageCommand.class);
        addressConverter = mock(AddressConverterPort.class);
        memberService = new MemberService(
                memberQuery,
                memberCommand,
                imageCommand,
                addressConverter
        );
    }

    @Test
    void modifyLocation_유효한_좌표_요청시_위치_변환() {
        // given
        Location newLocation = Location.of(37.4, 127.0);
        PassportHolder.setPassport(new Passport(1L, Role.MEMBER, null, null));

        Member member = Member.builder()
                .id(1L)
                .address(Address.of("서울시", "기홍구", "승빈동"))
                .location(Location.of(37.5, 127.5))
                .build();

        Address newAddress = Address.of("부산시", "수현구", "효은동");

        given(memberQuery.findById(1L)).willReturn(Optional.ofNullable(member));
        given(addressConverter.convertToAddress(newLocation))
                .willReturn(Optional.of(newAddress));

        // when
        memberService.modifyLocation(newLocation);

        // given
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        then(memberCommand).should().update(captor.capture());
        Member result = captor.getValue();
        assertThat(result.getLocation()).isEqualTo(newLocation);
        assertThat(result.getAddress()).isEqualTo(newAddress);
    }

}