package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.OAuthProvider;
import com.example.authserver.domain.JWT;

public interface OAuthLoginUseCase {

    JWT generateToken(String code, String state, OAuthProvider provider);

}
