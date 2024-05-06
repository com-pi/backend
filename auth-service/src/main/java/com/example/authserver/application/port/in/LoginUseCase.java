package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.LoginRequest;
import com.example.authserver.adapter.in.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginUseCase {

    LoginResponse login(LoginRequest loginRequest, HttpServletResponse response);

}
