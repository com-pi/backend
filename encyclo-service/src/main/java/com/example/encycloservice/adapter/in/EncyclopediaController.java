package com.example.encycloservice.adapter.in;

import com.example.common.baseentity.CommonResponse;
import com.example.encycloservice.application.EncyclopediaService;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "싹싹도감 서비스", description = "싹싹 도감을 이용합니다.")
public class EncyclopediaController {

    private final EncyclopediaService encyclopediaService;

    @PostMapping("/sync")
    @Operation(summary = "디비 동기화", description = "검색어에 따라 외부 서비스의 검색 결과와 동기화 합니다.")
    public ResponseEntity<CommonResponse<Void>> sync(
            @Parameter(description = "검색어", example = "장미")
            @RequestParam("keyword") String keyword) {
        int i = encyclopediaService.syncDatabase(keyword);
        return CommonResponse.okWithMessage(i + "건 동기화 되었습니다.", null);
    }

    @GetMapping("/plant")
    public ResponseEntity<CommonResponse<SearchPlantQueryResult>> searchPlant(
            @Parameter(description = "검색어", example = "칼랑코에")
            @RequestParam("keyword") String keyword,
            @Parameter(description = "페이지", example = "0")
            @RequestParam("page") int page,
            @Parameter(description = "사이즈", example = "10")
            @RequestParam int size) {
        SearchPlantQueryResult searchPlantQueryResult = encyclopediaService.searchByName(keyword, page, size);
        return CommonResponse.okWithMessage("검색 성공", searchPlantQueryResult);
    }

    @GetMapping("/plant/detail/{name}")
    public ResponseEntity<CommonResponse<PlantSpecies>> getDetail(@PathVariable String name){
        PlantSpecies plantDetailByName = encyclopediaService.getPlantDetailByName(name);
        return CommonResponse.okWithMessage("조회 성공", plantDetailByName);
    }

}
