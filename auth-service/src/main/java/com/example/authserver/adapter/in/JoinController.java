package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.JoinUseCase;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "회원가입 / 회원 및 비밀번호 찾기", description = "이메일, 비밀번호 및 휴대 전화번호 인증으로 회원가입을 진행합니다. <br>" +
        "인증된 번호를 통해 가입 이메일을 찾거나 비밀번호를 찾을 수 있습니다")
public class JoinController {

    // Todo : Validation 로직 추가 필요

    private final JoinUseCase joinUseCase;

    @Operation(summary = "회원가입", description = "회원 가입을 진행합니다. <br>" +
            "인증되지 않은 번호를 입력하면 예외를 반환합니다.")
    @PostMapping()
    public ResponseEntity<CommonResponse> join(JoinRequest joinRequest) {

        joinUseCase.join(joinRequest);

        return CommonResponse.okWithMessage("회원가입이 정상 처리되었습니다.");
    }

    @Operation(summary = "이메일 중복 검사", description = "이메일 중복검사를 진행합니다.")
    @GetMapping("/is_duplicate")
    public ResponseEntity<CommonResponse> joinNumber(
            @RequestParam("email") String email) {

        boolean isDuplicated = joinUseCase.isDuplicateEmail(email);

        return CommonResponse.okWithMessage(
                isDuplicated ? "이미 존재하는 이메일 주소입니다." : "중복되지 않은 이메일 주소입니다."
        );
    }

    @Operation(summary = "전화번호 sms 인증", description = "휴대 전화번호를 인증을 요청 합니다.")
    @GetMapping(value = "/phone_number_verification")
    public ResponseEntity<CommonResponse> requestPhoneNumberVerification(
            @ParameterObject VerifyPhoneNumberRequest request){

        String code = joinUseCase.requestNumberVerification(request);

        return CommonResponse.okWithMessage("인증번호가 발송되었습니다. 인증번호는 3분간 유효합니다. : " + code);
    }

    @Operation(summary = "인증 코드 등록", description = "문자로 발송된 인증 코드를 등록합니다.")
    @PostMapping("/verification_code")
    public ResponseEntity<CommonResponse> verifyCode(
            @RequestParam(name = "이메일 주소") String email,
            @RequestParam(name = "핸드폰 번호") String phoneNumber,
            @RequestParam(name = "인증 코드") String verificationCode){

        joinUseCase.verifyCode(email, phoneNumber, verificationCode);

        return CommonResponse.okWithMessage("인증이 완료되었습니다.");
    }


}
