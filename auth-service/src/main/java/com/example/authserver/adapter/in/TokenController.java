package com.example.authserver.adapter.in;

import com.example.authserver.application.TokenReissueService;
import com.example.authserver.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TokenController {

    private final TokenReissueService tokenReissueService;

    @GetMapping("/token")
    public ResponseEntity<TokenReIssueResponse> tokenReissue(HttpServletRequest request){

        TokenReIssueResponse reissuedToken = tokenReissueService.reissueToken(CookieUtil.getRefreshToken(request));

        return ResponseEntity.ok(reissuedToken);
    }

}
