package com.example.encycloservice.adapter.in;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.application.port.in.IdentifyPlantUseCase;
import com.example.encycloservice.application.port.in.ScrapeUseCase;
import com.example.encycloservice.adapter.out.PlantDetailResult;
import com.example.encycloservice.adapter.out.SearchPlantResultList;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap/drfull")
@Tag(name = "식물 정보 스크랩핑", description = "풀박사 어플리케이션에서 정보를 스크래핑 해옵니다.")
public class ScrapingController {

    private final ScrapeUseCase scrapeUseCase;
    private final IdentifyPlantUseCase identifyPlantUseCase;

    @GetMapping("/search")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<SearchPlantResultList>> search(
            @RequestParam("keyword")
            @Valid
            @Size(min = 2, message = "검색어가 너무 짧습니다.")
            @Parameter(description = "검색 키워드") String keyword) {

        SearchPlantResultList result = scrapeUseCase.searchPlant(keyword);

        return CommonResponse.okWithMessage("검색 성공", result);
    }

    @GetMapping("/detail")
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<PlantDetailResult>> scrapeDetail(
            @RequestParam("plantName")
            @Valid
            @Size(min = 2, message = "검색어가 너무 짧습니다.")
            @Parameter(description = "식물 이름") String plantName) {

        PlantDetailResult result = scrapeUseCase.scrapePlantDetail(plantName);

        return CommonResponse.okWithMessage("검색 성공", result);
    }

}
