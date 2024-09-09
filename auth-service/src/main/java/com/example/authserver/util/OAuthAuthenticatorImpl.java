package com.example.authserver.util;

import com.example.authserver.application.port.out.external.KakaoAuthClient;
import com.example.authserver.application.port.out.external.KakaoTokenClient;
import com.example.authserver.application.port.out.external.NaverAuthClient;
import com.example.authserver.application.port.out.external.NaverTokenClient;
import com.example.authserver.application.port.out.external.response.KakaoTokenResponse;
import com.example.authserver.application.port.out.external.response.KakaoUserInfoResponse;
import com.example.authserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.authserver.util.Secret.KAKAO_APP_KEY;
import static com.example.authserver.util.Secret.KAKAO_SECRET;

@Component
@RequiredArgsConstructor
public class OAuthAuthenticatorImpl implements OAuthAuthenticator {


    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoTokenClient kakaoTokenClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverTokenClient naverTokenClient;
    private static final String GRANT_TYPE = "authorization_code";

    @Override
    @Nullable
    public KakaoUserInfoResponse autheticate(String code, String redirectUrl) {

        final KakaoTokenResponse token = kakaoAuthClient.getAccessToken(KAKAO_APP_KEY, KAKAO_SECRET, code, redirectUrl, GRANT_TYPE);
        final KakaoUserInfoResponse kakaoUserInfo = kakaoTokenClient.getUserInfo("Bearer " + token.access_token());

        return kakaoUserInfo;
    }

}
