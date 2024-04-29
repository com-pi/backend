package com.example.authserver.controller;

import com.example.authserver.client.KakaoAuthClient;
import com.example.authserver.client.KakaoUserInfoResponse;
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

    @PostMapping("/kakao")
    public ResponseEntity<String> test(@RequestParam("token") String token){

        KakaoUserInfoResponse userInfo = kakaoAuthClient.getUserInfo("Bearer " + token);

        return ResponseEntity.ok(userInfo.toString());
    }


}
