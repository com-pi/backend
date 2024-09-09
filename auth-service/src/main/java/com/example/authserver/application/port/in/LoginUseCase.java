package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.util.AuthenticateResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginUseCase {

    AuthenticateResponse login(LoginRequest loginRequest);

}
