package com.example.authserver.application;

import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.application.port.in.OAuthLoginUseCase;
import com.example.authserver.application.port.out.external.*;
import com.example.authserver.application.port.out.external.response.KakaoTokenResponse;
import com.example.authserver.application.port.out.external.response.KakaoUserInfoResponse;
import com.example.authserver.application.port.out.external.response.NaverTokenResponse;
import com.example.authserver.application.port.out.external.response.NaverUserInfoResponse;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.application.util.JwtUtil;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.authserver.exception.AlreadyLoggedInException;
import com.example.authserver.exception.OAuthLoginException;
import com.example.authserver.util.CookieUtil;
import com.example.common.exception.ConflictException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.authserver.domain.TokenType.ACCESS_TOKEN;
import static com.example.authserver.domain.TokenType.REFRESH_TOKEN;
import static com.example.authserver.util.Secret.*;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoAuthClient.KakaoTokenClient kakaoTokenClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverAuthClient.NaverTokenClient naverTokenClient;
    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final JwtUtil jwtUtil;
    private final RedisPort redisPort;
    private static final String GRANT_TYPE = "authorization_code";

    // Todo clean 아키텍쳐 적용, port 에는 인터페이스만 둘 것.

    @Override
    @Transactional
    public LoginResponse kakaoLogin(
            String code,
            String redirectUrl,
            HttpServletRequest request,
            HttpServletResponse response) {

        doubleLoginCheck(request);

        KakaoTokenResponse token = kakaoAuthClient.getAccessToken(KAKAO_APP_KEY, KAKAO_SECRET, code, redirectUrl, GRANT_TYPE);
        KakaoUserInfoResponse kakaoUserInfo = kakaoTokenClient.getUserInfo("Bearer " + token.access_token());
        Optional<Member> kakaoMember = memberQuery.findByKakaoId(kakaoUserInfo.id().toString());

        boolean isNewMember = kakaoMember.isEmpty();

        if(isNewMember) {
            Optional<Member> byEmail = memberQuery.findByEmail(kakaoUserInfo.kakao_account().email());

            if (byEmail.isPresent()) {
                if(byEmail.get().getKakaoId() != null) {
                    throw new ConflictException("카카오 계정으로 가입된 회원입니다.");
                }
                throw new ConflictException("이메일/비밀번호를 통해 가입된 계정입니다.");
            }
            Member newMember = Member.createSocial(kakaoUserInfo.toMemberCreate());
            Member savedMember = memberCommand.save(newMember);
            kakaoMember = Optional.of(savedMember);
        }

        ComPToken refreshToken = jwtUtil.generateToken(kakaoMember.get(), REFRESH_TOKEN);
        redisPort.saveRefreshToken(kakaoMember.get(), refreshToken);
        CookieUtil.setRefreshCookie(refreshToken, response);

        ComPToken accessToken = jwtUtil.generateToken(kakaoMember.get(), ACCESS_TOKEN);

        return LoginResponse.of(accessToken, isNewMember);
    }

    @Override
    @Transactional
    public LoginResponse naverLogin(
            String code,
            String state,
            HttpServletRequest request,
            HttpServletResponse response) {

        doubleLoginCheck(request);
        NaverUserInfoResponse naverUserInfo;

        try {
            NaverTokenResponse naverResponse = naverAuthClient.getAccessToken(
                    code, state, NAVER_APP_KEY, NAVER_SECRET, GRANT_TYPE);

            naverUserInfo = naverTokenClient.getUserInfo(
                    "Bearer " + naverResponse.access_token());
        } catch (FeignException e) {
            throw new OAuthLoginException(e);
        }

        Optional<Member> naverMember = memberQuery.findByNaverId(naverUserInfo.response().id());
        Boolean isNewMember = naverMember.isEmpty();

        // Todo: 서비스간 정합성 맞추기
        if(isNewMember) {
            Optional<Member> byEmail = memberQuery.findByEmail(naverUserInfo.response().email());
            if (byEmail.isPresent()) {
                if(byEmail.get().getNaverId() != null) {
                    throw new ConflictException("네이버 계정으로 가입된 회원입니다.");
                }
                throw new ConflictException("이메일/비밀번호를 통해 가입된 계정입니다.");
            }
            Member newMember = Member.createSocial(naverUserInfo.toDomain());
            Member savedMember = memberCommand.save(newMember);
            naverMember = Optional.of(savedMember);
        }

        ComPToken refreshToken = jwtUtil.generateToken(naverMember.get(), TokenType.REFRESH_TOKEN);
        redisPort.saveRefreshToken(naverMember.get(), refreshToken);
        CookieUtil.setRefreshCookie(refreshToken, response);
        ComPToken accessToken = jwtUtil.generateToken(naverMember.get(), TokenType.ACCESS_TOKEN);

        return LoginResponse.of(accessToken, isNewMember);
    }

    private void doubleLoginCheck(HttpServletRequest request) {
        Optional<String> refreshToken = CookieUtil.getRefreshToken(request);
        if(refreshToken.isPresent()) {
            refreshToken
                    .flatMap(token -> jwtUtil.validateToken(token, REFRESH_TOKEN))
                    .ifPresent(passport -> {
                        throw new AlreadyLoggedInException("이미 로그인 되어있습니다.");
                    });
        }
    }

}