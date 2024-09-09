package com.example.authserver.application;

import com.example.authserver.application.port.in.OAuthLoginUseCase;
import com.example.authserver.application.port.out.external.response.KakaoUserInfo;
import com.example.authserver.application.port.out.external.response.NaverUserInfo;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.util.Authenticator;
import com.example.authserver.domain.AuthenticateResult;
import com.example.authserver.domain.Member;
import com.example.authserver.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final Authenticator authenticator;
    private final TokenIssuer tokenIssuer;

    @Override
    @Transactional
    public AuthenticateResult kakaoLogin (
            final String code,
            final String redirectUrl) {

        KakaoUserInfo kakaoUserInfo = authenticator.oAuthenticate(code, redirectUrl);
        Optional<Member> kakaoMember = memberQuery.findByKakaoId(kakaoUserInfo.id().toString());

        if(kakaoMember.isEmpty()) {
            Member newMember = Member.createSocial(kakaoUserInfo.toMemberCreate());
            Member savedMember = memberCommand.save(newMember);
            kakaoMember = Optional.of(savedMember);
        }

        TokenPair tokenPair = tokenIssuer.issueToken(kakaoMember.get());
        memberCommand.update(kakaoMember.get().loginStamp(LocalDateTime.now()));
        return new AuthenticateResult(HttpStatus.OK, tokenPair, kakaoMember.get().getLastLogin());
    }

    @Override
    @Transactional
    public AuthenticateResult naverLogin(
            String code,
            String state) {

        NaverUserInfo naverUserInfo = authenticator.authenticate(code, state);
        Optional<Member> naverMember = memberQuery.findByNaverId(naverUserInfo.response().id());

        if(naverMember.isEmpty()) {
            Member newMember = Member.createSocial(naverUserInfo.toDomain());
            Member savedMember = memberCommand.save(newMember);
            naverMember = Optional.of(savedMember);
        }

        TokenPair tokenPair = tokenIssuer.issueToken(naverMember.get());
        memberCommand.update(naverMember.get().loginStamp(LocalDateTime.now()));
        return new AuthenticateResult(HttpStatus.OK, tokenPair, naverMember.get().getLastLogin());
    }

}