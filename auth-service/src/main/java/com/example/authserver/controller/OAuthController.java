package com.example.authserver.controller;

import com.example.authserver.client.KakaoAuthClient;
import com.example.authserver.client.KakaoUserInfoResponse;
import com.example.authserver.client.NaverAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final KakaoAuthClient kakaoAuthClient;
    private final NaverAuthClient naverAuthClient;

    @PostMapping("/kakao")
    public ResponseEntity<String> getKakaoProfileFromAuthCode(@RequestParam("token") String token){

        KakaoUserInfoResponse userInfo = kakaoAuthClient.getUserInfo("Bearer " + token);

        return ResponseEntity.ok(userInfo.toString());
    }

    @PostMapping("/naver")
    public ResponseEntity<String> getNaverProfileFromAuthCode(@RequestParam("code") String code, @RequestParam("state") String state){

        String accessToken = naverAuthClient
                .getAccessToken(code, state, "OhHssSBOwmc929lDfGOg", "JIZ1uNAFt7", "authorization_code");

        return ResponseEntity.ok(accessToken);
    }


}
