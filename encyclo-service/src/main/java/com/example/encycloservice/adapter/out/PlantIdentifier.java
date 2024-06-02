package com.example.encycloservice.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(url = "https://my-api.plantnet.org", name = "plantnet")
public interface PlantIdentifier {

    @PostMapping("/v2/identify/{project}")
    PlantIdentificationResult identify(
            @PathVariable("project") String project,
            @RequestParam("api-key") String apiKey,
            @RequestPart("images") List<MultipartFile> images
    );

}
