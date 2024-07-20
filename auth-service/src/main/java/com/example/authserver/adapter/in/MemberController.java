package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.LocationRequest;
import com.example.authserver.adapter.in.request.ModifyMemberInfoRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    
    @Operation(summary = "회원 정보 변경")
    @Authenticate(Role.MEMBER)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Void>> modifyMember(
            @Parameter(description = "닉네임, 12 자 이내", required = true)
            @RequestPart(value = "nickName") String nickName,
            @Parameter(description = "소개글, 3,000 자 이내")
            @RequestPart(value = "introduction", required = false) String introduction,
            @Parameter(description = "위도, 33 ~ 38")
            @RequestPart(value = "latitude", required = false) String latitude,
            @Parameter(description = "경도, 125 ~ 132")
            @RequestPart(value = "longitude", required = false) String longitude,
            @Parameter(description = "프로필 사진 업로드 여부")
            @RequestPart(value = "isPicUploaded") Boolean isPicUploaded,
            @Parameter(description = "사진")
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        Passport passport = PassportHolder.getPassport();

        LocationRequest location = LocationRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        ModifyMemberInfoRequest modifyMemberInfoRequest = ModifyMemberInfoRequest.builder()
                .memberId(passport.memberId())
                .nickname(nickName)
                .introduction(introduction)
                .location(location)
                .isPicUploaded(isPicUploaded)
                .profileImage(profileImage)
                .build();

        memberUseCase.modifyMemberInfoRequest(modifyMemberInfoRequest);

        return CommonResponse.okWithMessage("회원 정보 변경 성공", null);
    }



}
