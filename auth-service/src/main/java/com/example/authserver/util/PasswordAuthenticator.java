package com.example.authserver.util;

import com.example.authserver.domain.Member;

public interface PasswordAuthenticator {

    AuthenticateResponse authenticate(Member member, String password);

}
