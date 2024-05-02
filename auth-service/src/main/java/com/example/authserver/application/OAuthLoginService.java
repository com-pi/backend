package com.example.authserver.application;

import com.example.authserver.adapter.in.OAuthProvider;
import com.example.authserver.application.port.in.OAuthLoginUseCase;
import com.example.authserver.application.port.out.external.KakaoAuthClient;
import com.example.authserver.application.port.out.external.NaverAuthClient;
import com.example.authserver.domain.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    private final KakaoAuthClient kakaoAuthClient;
    private final NaverAuthClient naverAuthClient;

    @Override
    public JWT generateToken(String code, String state, OAuthProvider provider) {

        switch (provider){
            case KAKAO:

        }

        return null;
    }




}
