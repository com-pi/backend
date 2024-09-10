package com.example.authserver.application.util;

import com.example.authserver.application.port.out.external.response.KakaoUserInfo;
import com.example.authserver.application.port.out.external.response.NaverUserInfo;
import com.example.authserver.domain.Member;

public interface Authenticator {

    KakaoUserInfo oAuthenticate(String code, String redirectUrl);
    NaverUserInfo authenticate(String code, String state);
    Boolean authenticate(Member member, String password);

}
