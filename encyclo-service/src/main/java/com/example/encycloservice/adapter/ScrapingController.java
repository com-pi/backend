package com.example.encycloservice.adapter;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.encycloservice.application.port.in.ScrapeUseCase;
import com.example.encycloservice.domain.PlantDetailResult;
import com.example.encycloservice.domain.SearchPlantResultList;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap/drfull")
@Tag(name = "식물 정보 스크랩핑", description = "풀박사 어플리케이션에서 정보를 스크래핑 해옵니다.")
public class ScrapingController {

    private final ScrapeUseCase scrapeUseCase;

    @GetMapping("/search")
    @Authenticate(Role.ADMIN)
    public ResponseEntity<CommonResponse<SearchPlantResultList>> search(
            @RequestParam("keyword")
            @Valid
            @Size(min = 2, message = "검색어가 너무 짧습니다.")
            @Parameter(description = "검색 키워드") String keyword) {

        SearchPlantResultList result = scrapeUseCase.searchPlant(keyword);

        return CommonResponse.okWithMessage("검색 성공", result);
    }

    @GetMapping("/detail")
    @Authenticate(Role.ADMIN)
    public ResponseEntity<CommonResponse<PlantDetailResult>> scrapeDetail(
            @RequestParam("plantName")
            @Valid
            @Size(min = 2, message = "검색어가 너무 짧습니다.")
            @Parameter(description = "식물 이름") String plantName) {

        PlantDetailResult result = scrapeUseCase.scrapePlantDetail(plantName);

        return CommonResponse.okWithMessage("검색 성공", result);
    }

}
