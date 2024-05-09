package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.JoinUseCase;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이메일, 비밀번호를 이용하여 회원가입을 진행합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
@Tag(name = "회원가입", description = "이메일, 비밀번호로 회원가입을 진행합니다.")
public class JoinController {

    private final JoinUseCase joinUseCase;

    @Operation(summary = "회원가입")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> join(JoinRequest joinRequest) {

        joinUseCase.join(joinRequest);

        return CommonResponse.okWithMessage("회원가입이 정상 처리되었습니다.");
    }


}
