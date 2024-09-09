package com.example.authserver.application;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.util.AuthenticateResponse;
import com.example.authserver.util.JwtUtilImpl;
import com.example.authserver.application.port.in.LoginUseCase;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.authserver.util.CookieUtil;
import com.example.authserver.util.PasswordAuthenticator;
import com.example.common.exception.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final MemberQuery memberQuery;
    private final PasswordAuthenticator passwordAuthenticator;

    @Override
    @Transactional
    public AuthenticateResponse login(LoginRequest loginRequest) {

        final Member member = memberQuery.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException(Member.class));

        return passwordAuthenticator.authenticate(member, loginRequest.password());
    }
}
