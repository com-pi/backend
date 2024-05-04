package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.LoginResponse;
import com.example.authserver.application.port.in.OAuthLoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 소셜 로그인 API
 *
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Tag(name = "소셜 로그인", description = "OAuth 로그인을 통해 받은 인가코드를 받아 회원정보를 로드하고, 인증을 위한 JWT 을 반환합니다. <br>" +
        "AccessToken 는 ResponseBody 로, RefreshToken 은 Cookie 로 전달합니다.")
public class OAuthController {

    private final OAuthLoginUseCase oAuthLoginUseCase;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> OAuthLoginWithKakao(
            @RequestParam("code") String code,
            @RequestParam("redirect_url") String redirectUrl,
            HttpServletResponse response){

        // Todo. validation : 요청에 리프레시 토큰이 포함된 경우 예외처리 / 토큰폐기 / 무시 등 처리필요 --> 게이트웨이 연계 처리
        // 요청에 리프레시 토큰이 없는 경우 (정상 요청)
        LoginResponse loginResponse = oAuthLoginUseCase.kakaoLogin(code, redirectUrl, response);

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "네이버 로그인")
    @PostMapping("/naver")
    public ResponseEntity<LoginResponse> OAuthLoginWithNaver (
        @RequestParam("code") String code,
        @RequestParam("state") String state,
        HttpServletResponse response) {

        // Todo. validation : 요청에 리프레시 토큰이 포함된 경우 예외처리 / 토큰폐기 / 무시 등 처리필요 --> 게이트웨이 연계 처리
        // 요청에 리프레시 토큰이 없는 경우 (정상 요청)
        LoginResponse loginResponse = oAuthLoginUseCase.naverLogin(code, state, response);

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "이메일/비밀번호 로그인")
    @GetMapping("/email")
    public ResponseEntity<LoginResponse> LoginWithIdPwd (){
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/email")
    public ResponseEntity<LoginResponse> LoginWithEmail (){
        return ResponseEntity.ok(null);
    }


}
