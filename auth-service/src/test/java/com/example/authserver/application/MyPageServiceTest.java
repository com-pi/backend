package com.example.authserver.application;

import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.imagemodule.application.port.ImageCommand;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.BDDMockito.mock;

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

}