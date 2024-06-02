package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.*;
import com.example.authserver.application.ForgetService;
import com.example.authserver.domain.ComPToken;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forget")
@Tag(name = "이메일/비밀번호 찾기", description = "이메일, 비밀번호를 찾습니다.")
public class ForgetController {

    private final ForgetService forgetService;

    @Operation(summary = "가입 이메일 찾기 - 인증번호 발송", description = "회원가입시 등록한 핸드폰 번호로 인증코드를 전송합니다.")
    @PostMapping("/email")
    public ResponseEntity<CommonResponse<String>> findId(
            @RequestBody @Valid FindIdRequest request
    ){
        String verificationCode = forgetService.findId(request);

        return CommonResponse.okWithMessage("인증번호가 발송되었습니다. 인증번호는 3분간 유효합니다.", verificationCode);
    }

    @Operation(summary = "가입 이메일 찾기 - 인증번호 인증", description = "전송된 인증번호를 입력하여 아이디를 찾아옵니다.")
    @GetMapping("/email")
    public ResponseEntity<CommonResponse<String>> verifyCode(
            @ParameterObject @Valid VerifyCodeForEmailRequest request
    ) {
        String email = forgetService.verifyFindIdCode(request);

        return CommonResponse.okWithMessage("인증에 성공했습니다", email);
    }

    @Operation(summary = "비밀번호 변경 - 인증번호 발송", description = "회원 가입시 등록한 핸드폰 번호로 인증코드를 전송합니다.")
    @PostMapping("/password")
    public ResponseEntity<CommonResponse<String>> findPassword(
            @RequestBody @Valid FindPasswordRequest request
    ){
        String verificationCode = forgetService.findPassword(request);

        return CommonResponse.okWithMessage("인증번호가 발송되었습니다. 인증번호는 3분간 유효합니다.", verificationCode);
    }


    @Operation(summary = "비밀번호 변경 - 인증번호 인증", description = "전송된 인증번호를 입력하여 확인하고 새로운 비밀번호 등록을 위함 임시 토큰을 반환합니다.<br>" +
            "이후 비밀번호 변경 페이지에서 비밀번호 변경 요청시 를 다시 토큰을 전달하도록 합니다.")
    @GetMapping("/password")
    public ResponseEntity<CommonResponse<ComPToken>> verifyPassword(
            @ParameterObject @Valid VerifyFindPasswordCodeRequest request
    ){
        ComPToken comPToken = forgetService.verifyPasswordCode(request);

        return CommonResponse.okWithMessage("인증번호 확인 완료. 비밀번호 변경을 위한 토큰을 발급합니다", comPToken);
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다. 발급된 토큰으로 신원을 확인 합니다.")
    @PatchMapping("/password")
    public ResponseEntity<CommonResponse<Void>> verifyPassword(
            @ParameterObject @Valid ChangePasswordRequest request
    ){
        forgetService.changePassword(request);

        return CommonResponse.okWithMessage("새 비밀번호로 변경 되었습니다.", null);
    }

}
