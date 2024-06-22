package com.example.encycloservice.adapter.in;

import com.example.common.baseentity.CommonResponse;
import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.application.port.in.StatisticsUseCase;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "싹싹도감 통계 api", description = "최근 검색어, 인기식물 등 통계")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatisticsController {

    private final StatisticsUseCase statisticsUseCase;

    @Operation(summary = "최근 상세정보가 조회된 식물 조회", description = "최근에 상세정보가 조회된 식물을 조회합니다.")
    @GetMapping("/recent-plant-detail")
    public ResponseEntity<CommonResponse<RecentPlantDetailStat>> getRecentDetailRequestStat(
            @Parameter(name = "페이지", description = "기본값 1")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(name = "사이즈", description = "기본값 10")
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        RecentPlantDetailStat recentPlantDetailsList = statisticsUseCase.getRecentPlantDetailsList(page, size);
        return CommonResponse.okWithMessage("최근 조회된 식물 조회 성공", recentPlantDetailsList);
    }

    @Operation(summary = "인기 식물 조회", description = "인기 식물을 조회합니다.")
    @GetMapping("/popular-plant")
    public ResponseEntity<CommonResponse<PopularPlantStatResponse>> getPopularPlantStat() {
        PopularPlantStatResponse popularPlantList = statisticsUseCase.getPopularPlantList();
        return CommonResponse.okWithMessage("인기 식물 조회 성공", popularPlantList);
    }

    @Operation(summary = "인기 식물 업데이트", description = "인기 식물을 업데이트 합니다.")
    @PostMapping("/popular-planth")
    public ResponseEntity<CommonResponse<Void>> addPopularPlantStat() {
        statisticsUseCase.updatePopularPlantList(LocalDateTime.now());
        return CommonResponse.okWithMessage("업데이트 요청 완료", null);
    }

}