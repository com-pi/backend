package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.application.port.in.OAuthLoginUseCase;
import com.example.authserver.domain.AuthenticateResult;
import com.example.authserver.util.CookieUtil;
import com.example.common.baseentity.CommonResponse;
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
@Tag(name = "소셜 로그인", description = "OAuth 로그인을 통해 받은 인가코드를 받아 회원정보를 로드하고, 인증을 위한 JWT 을 반환합니다. <br>" +
        "AccessToken 는 ResponseBody 로, RefreshToken 은 Cookie 로 전달합니다.")
public class OAuthController {

    private final OAuthLoginUseCase oAuthLoginUseCase;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao")
    public ResponseEntity<CommonResponse<LoginResponse>> OAuthLoginWithKakao(
            @RequestParam("code") String code,
            @RequestParam("redirect_url") String redirectUrl,
            HttpServletResponse response){

        AuthenticateResult authenticateResult = oAuthLoginUseCase.kakaoLogin(code, redirectUrl);

        CookieUtil.setRefreshCookie(authenticateResult.tokenPair().refreshToken(), response);
        return CommonResponse.okWithMessage("로그인에 성공하였습니다.", authenticateResult.toLoginResponse());
    }

    @Operation(summary = "네이버 로그인")
    @PostMapping("/naver")
    public ResponseEntity<CommonResponse<LoginResponse>> OAuthLoginWithNaver (
        @RequestParam("code") String code,
        @RequestParam(value = "state", required = false) String state,
        HttpServletResponse response) {

        AuthenticateResult authenticateResult = oAuthLoginUseCase.naverLogin(code, state);

        CookieUtil.setRefreshCookie(authenticateResult.tokenPair().refreshToken(), response);
        return CommonResponse.okWithMessage("로그인에 성공하였습니다.", authenticateResult.toLoginResponse());
    }

}
