package com.example.encycloservice.adapter.in;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.encycloservice.adapter.in.response.PlantSpeciesDetailResponse;
import com.example.encycloservice.application.EncyclopediaService;
import com.example.encycloservice.application.PlantBriefListResponse;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

}
