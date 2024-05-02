package com.example.authserver.application.port.out.external;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kakao-auth", url = "https://kapi.kakao.com")
public interface KakaoAuthClient {

}
