package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.LoginRequest;
import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.application.port.in.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "로그인", description = "이메일, 비밀번호로 로그인을 진행합니다.")
public class LoginController {

    private final LoginUseCase loginUseCase;

    @Operation(summary = "로그인")
    @PostMapping()
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response) {

        LoginResponse loginResponse = loginUseCase.login(request, response);

        return ResponseEntity.ok(loginResponse);
    }

}
