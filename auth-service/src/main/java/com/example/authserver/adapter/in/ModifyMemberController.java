package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.ModifyUseCase;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.AddressValue;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 정보 변경", description = "회원의 프로필 사진, 닉네임, 위치 등 정보를 변경합니다. <br>" +
        "회원 정보 변경을 위해 엑세스 토큰이 필요합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class ModifyMemberController {

    private final ModifyUseCase modifyUseCase;

    @Operation(summary = "닉네임, 소개글, 프로필 사진 변경")
    @PutMapping(value = "/info")
    @Authenticate(Role.USER)
    public ResponseEntity<CommonResponse<Void>> modifyMember(
            @Parameter(description = "닉네임")
            @RequestParam("nickname") @Nonnull @NotBlank String nickname,
            @Parameter(description = "소개글")
            @RequestParam("introduction") @Nonnull @NotBlank String introduction) {

        modifyUseCase.modifyMemberInfo(
                nickname,
                introduction
        );

        return CommonResponse.okWithMessage("회원 정보가 변경되었습니다.");
    }

    @Operation(summary = "회원 위치 변경")
    @Authenticate(Role.USER)
    @PatchMapping("/location")
    public ResponseEntity<CommonResponse<AddressValue>> modifyMemberLocation(
            @RequestBody @Valid ModifyLocationRequest request
    ) {
        AddressValue addressValue = modifyUseCase.modifyLocation(request);

        return CommonResponse.okWithMessage("회원 위치 정보가 변경되었습니다.", addressValue);
    }



}
