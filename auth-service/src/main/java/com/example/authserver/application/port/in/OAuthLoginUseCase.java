package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.LoginType;
import com.example.authserver.domain.ComPToken;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuthLoginUseCase {

    LoginResponse kakaoLogin(
            String code,
            String redirectUrl,
            HttpServletResponse response);

    LoginResponse naverLogin(
            String code,
            String state,
            HttpServletResponse response);

}
