package com.example.authserver.application;

import com.example.authserver.adapter.in.LoginRequest;
import com.example.authserver.adapter.in.LoginResponse;
import com.example.authserver.application.port.in.LoginUseCase;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.util.CookieUtil;
import com.example.authserver.util.JwtUtil;
import com.example.common.exception.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final MemberPort memberPort;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {

        Member member = memberPort.findByEmail(loginRequest.email()).orElseThrow(() ->
                new NotFoundException(Member.class));
        member.authenticateWithPassword(loginRequest.password(), passwordEncoder);

        ComPToken refreshToken = jwtUtil.generateToken(member, JwtUtil.TokenType.REFRESH_TOKEN);

        CookieUtil.setRefreshCookie(refreshToken, response);
        ComPToken accessToken = jwtUtil.generateToken(member, JwtUtil.TokenType.ACCESS_TOKEN);

        return LoginResponse.of(accessToken, member.loginStamp() == null);
    }
}
