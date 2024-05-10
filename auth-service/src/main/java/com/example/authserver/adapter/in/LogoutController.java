package com.example.authserver.adapter.in;

import com.example.authserver.application.LogoutService;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logout")
@Tag(name = "로그아웃", description = "리프레시 토큰을 삭제하여 로그아웃을 진행합니다.")
public class LogoutController {

    private final LogoutService logoutService;

    @DeleteMapping()
    public ResponseEntity<CommonResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response){

        logoutService.logout(request, response);

         return CommonResponse.okWithMessage("로그아웃 처리되었습니다.");
    }


}
