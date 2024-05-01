package com.example.authserver.service;

import com.example.authserver.client.KakaoAuthClient;
import com.example.authserver.client.KakaoUserInfoResponse;
import com.example.authserver.client.NaverAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KakaoAuthClient kakaoAuthClient;
    private final NaverAuthClient naverAuthClient;

    public void kakaoLogin(String authenticationCode) {

        KakaoUserInfoResponse userInfo = kakaoAuthClient.getUserInfo(authenticationCode);

    }

    public void naverLogin(String accessToken) {


    }

}
