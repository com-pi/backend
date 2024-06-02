package com.example.authserver.application;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.adapter.util.JwtUtilImpl;
import com.example.authserver.application.port.in.LoginUseCase;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.authserver.util.CookieUtil;
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

    private final MemberPort memberPort;
    private final JwtUtilImpl jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {

        Member member = memberPort.findByEmail(loginRequest.email()).orElseThrow(() ->
                new NotFoundException(MemberEntity.class));

        member.authenticateWithPassword(loginRequest.password(), passwordEncoder);

        ComPToken refreshToken = jwtUtil.generateToken(member, TokenType.REFRESH_TOKEN);

        CookieUtil.setRefreshCookie(refreshToken, response);
        ComPToken accessToken = jwtUtil.generateToken(member, TokenType.ACCESS_TOKEN);

        return LoginResponse.of(accessToken, member.loginStamp(LocalDateTime.now()) == null);
    }
}
