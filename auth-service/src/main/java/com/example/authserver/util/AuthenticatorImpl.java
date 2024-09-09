package com.example.authserver.util;

import com.example.authserver.application.port.out.external.KakaoAuthClient;
import com.example.authserver.application.port.out.external.KakaoTokenClient;
import com.example.authserver.application.port.out.external.NaverAuthClient;
import com.example.authserver.application.port.out.external.NaverTokenClient;
import com.example.authserver.application.port.out.external.response.KakaoTokenResponse;
import com.example.authserver.application.port.out.external.response.KakaoUserInfo;
import com.example.authserver.application.port.out.external.response.NaverTokenResponse;
import com.example.authserver.application.port.out.external.response.NaverUserInfo;
import com.example.authserver.application.util.Authenticator;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.OAuthLoginException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.authserver.util.Secret.*;
import static com.example.authserver.util.Secret.NAVER_SECRET;

@Component
@RequiredArgsConstructor
public class AuthenticatorImpl implements Authenticator {


    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoTokenClient kakaoTokenClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverTokenClient naverTokenClient;
    private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    private static final String GRANT_TYPE = "authorization_code";

    @Override
    @Nullable
    public KakaoUserInfo oAuthenticate(String code, String redirectUrl) {
        try {
            final KakaoTokenResponse token = kakaoAuthClient.getAccessToken(KAKAO_APP_KEY, KAKAO_SECRET, code, redirectUrl, GRANT_TYPE);
            return kakaoTokenClient.getUserInfo("Bearer " + token.access_token());
        } catch (FeignException e) {
            throw new OAuthLoginException(e);
        }
    }

    @Override
    @Nullable
    public NaverUserInfo authenticate(String code, String state) {
        try {
            NaverTokenResponse token = naverAuthClient.getAccessToken(code, state, NAVER_APP_KEY, NAVER_SECRET, GRANT_TYPE);
            return naverTokenClient.getUserInfo("Bearer " + token.access_token());
        } catch (FeignException e) {
            throw new OAuthLoginException(e);
        }
    }

    @Override
    public Boolean authenticate(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

}
