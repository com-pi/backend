package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.util.AuthenticateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuthLoginUseCase {

    AuthenticateResponse kakaoLogin(
            String code,
            String redirectUrl);

    LoginResponse naverLogin(
            String code,
            String state);

}
