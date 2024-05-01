package com.example.authserver.client;

/**
 * 카카오 사용자 정보를 받기 위한 레코드 입니다.

 */
public record KakaoUserInfoResponse(
        Long id,

        // Feign 클라이언트의 맵핑을 위해 스네이크 케이스를 사용하였습니다. (카멜케이스 인식 안됨)
        KakaoAccount kakao_account
){}
