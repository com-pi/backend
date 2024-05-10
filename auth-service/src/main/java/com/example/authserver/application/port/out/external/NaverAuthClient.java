package com.example.authserver.application.port.out.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-auth", url = "https://nid.naver.com")
public interface NaverAuthClient {

    @PostMapping("/oauth2.0/token")
    NaverTokenResponse getAccessToken(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") String grantType);

    @FeignClient(name = "naver-token", url = "https://openapi.naver.com")
    interface NaverTokenClient {

        @PostMapping("/v1/nid/me")
        NaverUserInfoResponse getUserInfo(@RequestHeader("Authorization") String accessToken);

    }

}