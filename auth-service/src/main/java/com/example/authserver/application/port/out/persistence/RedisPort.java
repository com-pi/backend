package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;
import com.example.common.domain.Passport;

public interface RedisPort {

    void saveRefreshToken(Member member, String refreshToken);
    String getRefreshToken(Passport passport);

}
