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
        "토큰 검증 단계에서 엑세스 토큰이 없거나 만료 되었는데 리프레시 토큰을 가지고 있으면 자동으로 재발급 시도를 합니다.")
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
