package com.example.encycloservice.adapter.in;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.encycloservice.adapter.in.request.PlantAddRequest;
import com.example.encycloservice.adapter.in.response.PlantAddInquiryResponse;
import com.example.encycloservice.adapter.in.response.PlantSpeciesDetailResponse;
import com.example.encycloservice.application.EncyclopediaService;
import com.example.encycloservice.application.PlantBriefListResponse;
import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantAddInquiryProcess;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "싹싹도감 서비스", description = "식물 정보를 검색 또는 동기화합니다.")
public class EncyclopediaController {

    private final EncyclopediaService encyclopediaService;

    @PostMapping("/sync")
    @Operation(summary = "디비 동기화", description = "검색어에 따라 외부 서비스의 검색 결과와 동기화 합니다.")
    public ResponseEntity<CommonResponse<Void>> sync(
            @Parameter(description = "검색어", example = "장미")
            @Valid @Size(min = 2)
            @RequestParam("keyword") String keyword) {
        encyclopediaService.syncDatabase(keyword);
        return CommonResponse.okWithMessage("동기화 요청되었습니다. 키워드 : " + keyword, null);
    }

    @GetMapping("/plant")
    @Operation(summary = "식물 검색", description = "식물 도감에서 식물을 검색합니다.")
    public ResponseEntity<CommonResponse<SearchPlantQueryResult>> searchPlant(
            @Parameter(description = "검색어", example = "칼랑코에")
            @Valid @Size(min = 2)
            @RequestParam("keyword") String keyword,
            @Parameter(description = "페이지", example = "0")
            @RequestParam("page") int page,
            @Parameter(description = "사이즈", example = "10")
            @Valid @Max(20)
            @RequestParam int size) {
        SearchPlantQueryResult searchPlantQueryResult = encyclopediaService.searchByName(keyword, page, size);
        return CommonResponse.okWithMessage("검색 성공", searchPlantQueryResult);
    }

    @GetMapping("/plant/detail/{id}")
    @Operation(summary = "식물 상세 정보 조회", description = "식물의 id 값으로 상세 정보를 조회 합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<PlantSpeciesDetailResponse>> getPlantDetailsById(@PathVariable Long id){
        PlantSpecies plantSpecies = encyclopediaService.getPlantDetailById(id);
        return CommonResponse.okWithMessage("조회 성공", PlantSpeciesDetailResponse.toResponse(plantSpecies));
    }

    @GetMapping("/plant/brief")
    @Operation(summary = "식물 간략 정보 조회", description = "식물의 id 값으로 간략 정보를 조회 합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<PlantBriefListResponse>> getPlantBriefById(@RequestParam List<Long> ids){
        PlantBriefListResponse plantBriefByIds = encyclopediaService.getPlantBriefByIds(ids);
        return CommonResponse.okWithMessage("조회 성공", plantBriefByIds);
    }

    @PostMapping("/plant/inquiry")
    @Operation(summary = "식물 추가 요청", description = "식물 추가를 요청합니다.")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<String>> addPlant(
            @RequestBody PlantAddRequest plantAddRequest
    ){
        encyclopediaService.savePlantAddInquiry(plantAddRequest);
        return CommonResponse.okWithMessage("식물 추가 요청이 완료되었습니다.", null);
    }

    @GetMapping("/plant/inquiry")
    @Operation(summary = "식물 추가 요청 조회", description = "식물 추가 요청을 조회합니다.")
    @Authenticate(Role.ADMIN)
    public ResponseEntity<CommonResponse<PlantAddInquiryResponse>> getPlantAddInquiry(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) PlantAddInquiry.Status status
    ){
        PlantAddInquiryResponse plantAddInquiry = encyclopediaService.getPlantAddInquiry(page, size, status);
        return CommonResponse.okWithMessage("식물 추가 요청 조회 성공", plantAddInquiry);
    }

    @PutMapping("/plant/inquiry/{inquiryId}")
    @Operation(summary = "식물 추가 요청 처리", description = "식물 추가 요청을 처리합니다.")
    @Authenticate(Role.ADMIN)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "식물 추가 요청 처리 정보\n" +
            "status : 처리 상태 (SUBMITTED, IN_PROGRESS, PROCESSED)")
    public ResponseEntity<CommonResponse<Void>> processPlantAddInquiry(
            @PathVariable Long inquiryId,
            @Schema(example = "{\"status\":\"PROCESSED\", \"result\":\"식물 도감에 추가 되었습니다. 국명: - 학명: - \"}")
            @RequestBody @Valid PlantAddInquiryProcess request
    ) {
        encyclopediaService.processPlantAddInquiry(inquiryId, request);
        return CommonResponse.okWithMessage("식물 추가 요청 처리가 완료되었습니다.", null);
    }

}
