package com.example.authserver.service;

import com.example.authserver.client.KakaoAuthClient;
import com.example.authserver.client.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KakaoAuthClient kakaoAuthClient;

    public void kakaoLogin(String accessToken) {

        KakaoUserInfoResponse userInfo = kakaoAuthClient.getUserInfo(accessToken);



    }

}
