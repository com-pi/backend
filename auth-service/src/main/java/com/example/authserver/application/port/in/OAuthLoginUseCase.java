package com.example.authserver.application.port.in;

import com.example.authserver.domain.AuthenticateResult;

public interface OAuthLoginUseCase {

    AuthenticateResult kakaoLogin(
            String code,
            String redirectUrl);

    AuthenticateResult naverLogin(
            String code,
            String state);

}
