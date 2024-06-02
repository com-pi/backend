package com.example.authserver.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 좌표를 행정동 주소로 변환해줍니다.
 */
@FeignClient(url = "https://dapi.kakao.com", name = "kakaoMapApi")
public interface KakaoMapClient {

    @GetMapping("/v2/local/geo/coord2regioncode.json")
    AddressConvertResponse getAddress(@RequestHeader String Authorization,
                       @RequestParam(required = false) double x,
                       @RequestParam(required = false) double y);

}
