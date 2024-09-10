package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.domain.AuthenticateResult;

public interface LoginUseCase {

    AuthenticateResult login(LoginRequest loginRequest);

}
