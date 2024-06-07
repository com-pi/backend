package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.ModifyInfoRequest;
import com.example.authserver.adapter.in.request.ModifyLocationRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Address;
import com.example.common.domain.Role;
import com.example.imagemodule.domain.ImageAndThumbnail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "회원 조회 / 회원 정보 변경", description = "자신 또는 다른 회원의 프로필(사진, 닉네임, 위치 등)을 조회하거나 <br>" +
        "자신의 정보를 변경합니다. 엑세스 토큰이 필요합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberUseCase memberUseCase;

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    @GetMapping
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<MyInfoResponse>> getMyInfo(){

        MyInfoResponse myInfo = memberUseCase.getMyInfo();

        return CommonResponse.okWithMessage("내 정보를 조회하였습니다.", myInfo);
    }

    @Operation(summary = "회원 정보 조회", description = "다른 회원의 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<MemberInfoResponse>> getOtherInfo(
            @PathVariable("memberId") Long memberId
    ){
        MemberInfoResponse memberInfo = memberUseCase.getMemberInfo(memberId);

        return CommonResponse.okWithMessage("내 정보를 조회하였습니다.", memberInfo);
    }

    @Operation(summary = "닉네임, 소개글 변경")
    @PutMapping(value = "/info")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<Void>> modifyMember(
            @RequestBody @Valid ModifyInfoRequest request
    ) {

        memberUseCase.modifyMyInfo(
                request.nickname(),
                request.introduction()
        );

        return CommonResponse.okWithMessage("회원 정보가 변경되었습니다.");
    }

    @Operation(summary = "프로필 사진 변경")
    @PostMapping(value = "/profileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<ImageAndThumbnail>> modifyMember(
            @Parameter(description = "프로필 사진")
            @RequestPart("profileImage") MultipartFile profileImage) {

        ImageAndThumbnail imageAndThumbnail = memberUseCase.postProfileImage(profileImage);

        return CommonResponse.okWithMessage("회원 사진이 변경되었습니다.", imageAndThumbnail);
    }


    @Operation(summary = "회원 위치 변경")
    @PatchMapping("/location")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<Address>> modifyMemberLocation(
            @RequestBody @Valid ModifyLocationRequest request
    ) {
        Address address = memberUseCase.modifyLocation(request.location().toDomain());

        return CommonResponse.okWithMessage("회원 위치 정보가 변경되었습니다.", address);
    }



}
