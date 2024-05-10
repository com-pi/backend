package com.example.authserver.adapter.in;

import com.example.authserver.application.TokenReissueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "토큰 재발급", description = "리프레시 토큰을 이용하여 토큰을 재발급 합니다. <br>" +
        "리프레시 토큰은 쿠키(HttpOnly)로 전달 됩니다.")
public class TokenController {

    private final TokenReissueService tokenReissueService;

    @Operation(summary = "토큰 재발급")
    @GetMapping("/token")
    public ResponseEntity<TokenReIssueResponse> tokenReissue(
            HttpServletRequest request,
            HttpServletResponse response){

        TokenReIssueResponse reissuedToken = tokenReissueService.reissueToken(request, response);

        return ResponseEntity.ok(reissuedToken);
    }

}
