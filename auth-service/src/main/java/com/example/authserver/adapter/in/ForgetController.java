package com.example.authserver.adapter.in;

import com.example.authserver.application.ForgetService;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forget")
@Tag(name = "아이디/비밀번호 찾기", description = "아이디 비밀번호를 찾습니다.")
public class ForgetController {

    private final ForgetService forgetService;

    @Operation(summary = "아이디 찾기 - 인증번호 발송", description = "회원가입시 등록한 핸드폰번호로 인증코드를 전송합니다.")
    @PostMapping("/id")
    public ResponseEntity<CommonResponse<String>> findId(
            @RequestBody FindIdRequest request
    ){
        String verificationCode = forgetService.findId(request);

        return CommonResponse.okWithMessage("인증번호가 발송되었습니다. 인증번호는 3분간 유효합니다.", verificationCode);
    }

    @Operation(summary = "아이디 찾기 - 인증번호 인증", description = "전송된 번호를 입력하여 아이디를 찾아옵니다.")
    @GetMapping("/id")
    public ResponseEntity<CommonResponse<String>> verifyCode(
            @ParameterObject VerifyCodeRequest request
    ) {

        String email = forgetService.verifyCode(request);

        if (email != null) {
            return CommonResponse.okWithMessage("인증에 성공했습니다", email);
        }

        return CommonResponse.badRequestWithMessage("인증번호가 틀렸습니다.", null);
    }


}
