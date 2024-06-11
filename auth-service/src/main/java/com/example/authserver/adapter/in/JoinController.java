package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.MemberCreateRequest;
import com.example.authserver.adapter.in.request.VerifyCodeForJoinRequest;
import com.example.authserver.adapter.in.request.VerifyPhoneNumberRequest;
import com.example.authserver.application.port.in.JoinUseCase;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 이메일, 비밀번호를 이용하여 회원가입을 진행합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
@Tag(name = "회원가입", description = "이메일, 비밀번호 및 휴대 전화번호 인증으로 회원가입을 진행합니다. <br>" +
        "인증된 번호를 통해 가입 이메일을 찾거나 비밀번호를 찾을 수 있습니다")
public class JoinController {

    private final JoinUseCase joinUseCase;

    @Operation(summary = "회원가입 - 1. 핸드폰 번호 sms 인증코드 발송", description = "휴대 전화번호를 인증을 요청 합니다.")
    @PostMapping(value = "/phone_number_verification")
    public ResponseEntity<CommonResponse<String>> requestPhoneNumberVerification(
            @RequestBody @Valid VerifyPhoneNumberRequest request){

        String code = joinUseCase.requestNumberVerification(request);

        return CommonResponse.okWithMessage("인증번호가 발송되었습니다. 인증번호는 3분간 유효합니다.", code);
    }

    @Operation(summary = "회원가입 - 2. 핸드폰 번호 sms 인증코드 인증", description = "문자로 발송된 인증 코드를 전송합니다.")
    @GetMapping(value = "/verification_code")
    public ResponseEntity<CommonResponse<Void>> verifyCode(
            @ParameterObject @Valid VerifyCodeForJoinRequest request
    ){
        joinUseCase.verifyCode(request);

        return CommonResponse.okWithMessage("인증이 완료되었습니다.");
    }

    @Operation(summary = "회원가입 - 3. 회원가입", description = "회원 가입을 진행합니다. <br>" +
            "인증되지 않은 번호를 입력하면 예외를 반환합니다.")
    @PostMapping()
    public ResponseEntity<CommonResponse<Void>> join(
            @RequestBody @Valid MemberCreateRequest memberCreateRequest) {

        joinUseCase.join(memberCreateRequest);

        return CommonResponse.okWithMessage("회원가입이 정상 처리되었습니다.");
    }

    @Operation(summary = "이메일 중복 검사", description = "이메일 중복검사를 진행합니다.")
    @GetMapping("/is_duplicate")
    public ResponseEntity<CommonResponse<Void>> checkEmailDuplication (
            @Parameter(description = "이메일 주소")
            @Valid @Email
            @RequestParam String email) {

        boolean isDuplicated = joinUseCase.checkEmailDuplication(email);

        return CommonResponse.okWithMessage(
                isDuplicated ? "이미 존재하는 이메일 주소입니다." : "중복되지 않은 이메일 주소입니다."
        );
    }

}
