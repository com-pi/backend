package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuthLoginUseCase {

    LoginResponse kakaoLogin(
            String code,
            String redirectUrl,
            HttpServletRequest request,
            HttpServletResponse response);

    LoginResponse naverLogin(
            String code,
            String state,
            HttpServletRequest request,
            HttpServletResponse response);

}
