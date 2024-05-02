package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.OAuthLoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 소셜 로그인 API
 *
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Tag(name = "소셜 로그인", description = "OAuth 로그인을 통해 받은 인가코드를 받아, 회원정보를 로드하여 Token 으로 반환합니다. \n" +
        "AccessToken 는 ResponseBody 에 전달하고, RefreshToken 은 Cookie 로 전달합니다.")
public class OAuthController {

    private final OAuthLoginUseCase OAuthLoginUseCase;


    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao")
    public ResponseEntity<GetTokenResponse> getTokenFromKakaoCode(
            @RequestParam("code") String token,
            @RequestParam("state") String state,
            HttpServletResponse response){

        // Todo. validation : 요청에 리프레시 토큰이 포함된 경우 예외처리 / 토큰폐기 / 무시 등 처리필요

        // 요청에 리프레시 토큰이 없는 경우 (정상 요청)


        return ResponseEntity.ok(userInfo.toString());
    }

    @Operation(summary = "네이버 로그인")
    @PostMapping("/naver")
    public ResponseEntity<String> getTokenFromNaverCode(@RequestParam("code") String code, @RequestParam("state") String state){


        return ResponseEntity.ok(accessToken);
    }

    @Operation(summary = "아이디/비밀번호 로그인")
    @PostMapping("/idpwd")
    public ResponseEntity<String>


}
