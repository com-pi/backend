package com.example.encycloservice.adapter.out.external;

import com.example.encycloservice.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(url = "https://my-api.plantnet.org", name = "plantnet", configuration = FeignConfig.class)
public interface PlantIdentifier {

    @PostMapping(value = "/v2/identify/{project}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    PlantIdentificationResult identify(
            @PathVariable("project") java.lang.String project,
            @RequestParam("api-key") java.lang.String apiKey,
            @RequestPart("images") List<MultipartFile> images
    );

}
