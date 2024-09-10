package com.example.authserver.application;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.util.Authenticator;
import com.example.authserver.domain.AuthenticateResult;
import com.example.authserver.util.*;
import com.example.authserver.application.port.in.LoginUseCase;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final Authenticator authenticator;
    private final TokenIssuer tokenIssuer;

    @Override
    public AuthenticateResult login(LoginRequest loginRequest) {

        Optional<Member> member = memberQuery.findByEmail(loginRequest.email());

        if(member.isEmpty()) {
            return new AuthenticateResult(HttpStatus.NOT_FOUND, null, null);
        }

        Boolean isPasswordCorrect = authenticator.authenticate(member.get(), loginRequest.password());
        if(!isPasswordCorrect) {
            return new AuthenticateResult(HttpStatus.BAD_REQUEST, null, null);
        }

        TokenPair tokenSet = tokenIssuer.issueToken(member.get());
        LocalDateTime lastLogin = member.get().getLastLogin();

        Member loginMember = member.get().loginStamp(LocalDateTime.now());
        memberCommand.update(loginMember);

        return new AuthenticateResult(HttpStatus.OK, tokenSet, lastLogin);
    }
}
