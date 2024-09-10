package com.example.authserver.application.port.out.external;

import com.example.authserver.application.port.out.external.response.NaverUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-token", url = "https://openapi.naver.com")
public
interface NaverTokenClient {

    @PostMapping("/v1/nid/me")
    NaverUserInfo getUserInfo(@RequestHeader("Authorization") String accessToken);

}
