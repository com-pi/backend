package com.example.authserver.application;

import com.example.authserver.application.fixture.FakeMemberPort;
import com.example.authserver.application.fixture.FakeRedisPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForgetServiceTest {

    @Test
    void findId_아이디_찾기_요청시_인증코드를_반환합니다(){
        // given
        ForgetService forgetService = new ForgetService(
                new FakeRedisPort(),
                new FakeMemberPort(),

        );


    }

}