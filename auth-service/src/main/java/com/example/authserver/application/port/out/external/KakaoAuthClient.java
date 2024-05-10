package com.example.authserver.application.port.out.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-auth", url = "https://kauth.kakao.com")
public interface KakaoAuthClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getAccessToken(
            @RequestParam("client_id") String appKey,
            @RequestParam("client_secret") String secret,
            @RequestParam("code") String code,
            @RequestParam("redirect_url") String redirecUrl,
            @RequestParam("grant_type") String grantType);

    @FeignClient(name = "kakao-token", url = "https://kapi.kakao.com")
    interface KakaoTokenClient {
        @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        KakaoUserInfoResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
    }

}