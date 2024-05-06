package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.LoginUseCase;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginUseCase loginUseCase;

    @GetMapping()
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {

        LoginResponse loginResponse = loginUseCase.login(request, response);

        return ResponseEntity.ok(loginResponse);
    }

}
