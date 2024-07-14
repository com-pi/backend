package com.example.encycloservice.adapter.in;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.encycloservice.adapter.in.request.MyEncyclopediaCreateRequest;
import com.example.encycloservice.adapter.in.response.MyEncyclopediaListResponse;
import com.example.encycloservice.aop.filter.PassportHolder;
import com.example.encycloservice.application.port.in.MyEncyclopediaUseCase;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-encyclopedia")
@Tag(name = "내 도감 API", description = "이용자의 커스텀 도감을 관리하는 API")
public class MyEncyclopediaController {

    private final MyEncyclopediaUseCase myEncyclopediaUseCase;

    @GetMapping
    @Operation(summary = "내 도감 목록 조회", description = "내 도감을 조회합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<MyEncyclopediaListResponse>> getMyEncyclopediaList() {
        Passport passport = PassportHolder.getPassport();
        List<MyEncyclopedia> myEncyclopedias = myEncyclopediaUseCase.myEncyclopediaList(passport.memberId());

        MyEncyclopediaListResponse response = new MyEncyclopediaListResponse(
                myEncyclopedias.stream().map(MyEncyclopediaListResponse.MyEncyclopediaResponse::fromDomain).toList());

        return CommonResponse.okWithMessage("내 도감 목록 조회 성공", response);
    }

    @GetMapping("/{myEncyclopediaId}")
    @Operation(summary = "내 도감 조회", description = "내 도감을 조회합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<MyEncyclopediaDetailResponse>> getMyEncyclopedia(
            @PathVariable Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaUseCase.getMyEncyclopediaDetail(myEncyclopediaId);
        MyEncyclopediaDetailResponse response = MyEncyclopediaDetailResponse.fromDomain(myEncyclopedia);
        return CommonResponse.okWithMessage("내 도감 조회 성공", response);
    }

    @PostMapping
    @Operation(summary = "내 도감 생성", description = "내 도감을 생성합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<Void>> addMyEncyclopedia(
            @RequestBody @Valid MyEncyclopediaCreateRequest request) {
        Passport passport = PassportHolder.getPassport();

        myEncyclopediaUseCase.createEncyclopedia(MyEncyclopediaCreate.builder()
                .memberId(passport.memberId())
                .title(request.title())
                .build());

        return CommonResponse.okWithMessage("내 도감 생성 성공");
    }

    @PostMapping("/{myEncyclopediaId}/plant")
    @Operation(summary = "내 도감에 식물 추가", description = "내 도감에 식물을 추가합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<Void>> addPlantsToMyEncyclopedia(
            @PathVariable Long myEncyclopediaId,
            @RequestParam List<Long> plantSpeciesIds) {
        myEncyclopediaUseCase.addPlantsToEncyclopedia(plantSpeciesIds, myEncyclopediaId);
        return CommonResponse.okWithMessage("내 도감에 식물 추가 성공");
    }

    @DeleteMapping("/{myEncyclopediaId}/plant")
    @Operation(summary = "내 도감에서 식물 삭제", description = "내 도감에서 식물을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<Void>> removePlantsFromMyEncyclopedia(
            @PathVariable Long myEncyclopediaId,
            @RequestParam List<Long> plantSpeciesIds) {
        myEncyclopediaUseCase.removePlantsFromEncyclopedia(plantSpeciesIds, myEncyclopediaId);
        return CommonResponse.okWithMessage("내 도감에서 식물 삭제 성공");
    }

}
