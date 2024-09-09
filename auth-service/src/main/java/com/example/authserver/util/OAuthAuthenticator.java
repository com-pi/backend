package com.example.authserver.util;

import com.example.authserver.application.port.out.external.response.KakaoUserInfoResponse;

public interface OAuthAuthenticator {

    KakaoUserInfoResponse autheticate(String code, String redirectUrl);
}
