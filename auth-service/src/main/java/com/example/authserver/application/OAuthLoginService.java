package com.example.authserver.application;

import com.example.authserver.adapter.in.LoginResponse;
import com.example.authserver.application.port.in.OAuthLoginUseCase;
import com.example.authserver.application.port.out.external.*;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.util.CookieUtil;
import com.example.authserver.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoAuthClient.KakaoTokenClient kakaoTokenClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverAuthClient.NaverTokenClient naverTokenClient;
    private final MemberPort memberPort;
    private final JwtUtil jwtUtil;
    private final RedisPort redisPort;
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.app-key}")
    private String kakaoAppKey;
    @Value("${oauth.kakao.secret}")
    private String kakaoSecret;
    @Value("${oauth.naver.app-key}")
    private String naverAppKey;
    @Value("${oauth.naver.secret}")
    private String naverSecret;


    @Override
    @Transactional
    public LoginResponse kakaoLogin(
            String code,
            String redirectUrl,
            HttpServletResponse response) {

        KakaoTokenResponse token = kakaoAuthClient.getAccessToken(
                kakaoAppKey, kakaoSecret, code, redirectUrl, GRANT_TYPE);
        KakaoUserInfoResponse kakaoUserInfo = kakaoTokenClient.getUserInfo(
                "Bearer " + token.access_token());

        Optional<Member> kakaoMember = memberPort.findByKakaoId(kakaoUserInfo.getId().toString());
        Boolean isNewMember = kakaoMember.isEmpty();

        // Todo: 서비스간 정합성 맞추기
        if (kakaoMember.isPresent()) {
            kakaoMember.get().updateProfileFromSocialProfile(kakaoUserInfo.getKakao_account().profile());
        } else {
            Member newKakaoMember = Member.newMemberForKakaoUser(kakaoUserInfo);
            memberPort.save(newKakaoMember);
            kakaoMember = Optional.of(newKakaoMember);
        }

        ComPToken refreshToken = jwtUtil.generateToken(kakaoMember.get(), JwtUtil.TokenType.REFRESH_TOKEN);
        redisPort.saveRefreshToken(kakaoMember.get(), refreshToken.getToken());
        CookieUtil.setRefreshCookie(refreshToken, response);

        ComPToken accessToken = jwtUtil.generateToken(kakaoMember.get(), JwtUtil.TokenType.ACCESS_TOKEN);

        return LoginResponse.of(accessToken, isNewMember);
    }

    @Override
    @Transactional
    public LoginResponse naverLogin(String code, String state, HttpServletResponse response) {

        NaverTokenResponse naverResponse = naverAuthClient.getAccessToken(
                code, state, naverAppKey, naverSecret, GRANT_TYPE);
        NaverUserInfoResponse naverUserInfo = naverTokenClient.getUserInfo(
                "Bearer " + naverResponse.access_token());

        Optional<Member> naverMember = memberPort.findByNaverId(naverUserInfo.getResponse().id());
        Boolean isNewMember = naverMember.isEmpty();

        // Todo: 서비스간 정합성 맞추기
        if (naverMember.isPresent()) {
            naverMember.get().updateProfileFromSocialProfile(naverUserInfo.getResponse());
        } else {
            Member newNaverMember = Member.newMemberForNaverUser(naverUserInfo);
            memberPort.save(newNaverMember);
            naverMember = Optional.of(newNaverMember);
        }

        ComPToken refreshToken = jwtUtil.generateToken(naverMember.get(), JwtUtil.TokenType.REFRESH_TOKEN);
        redisPort.saveRefreshToken(naverMember.get(), refreshToken.getToken());
        ComPToken accessToken = jwtUtil.generateToken(naverMember.get(), JwtUtil.TokenType.ACCESS_TOKEN);

        return LoginResponse.of(accessToken, isNewMember);
    }


}
