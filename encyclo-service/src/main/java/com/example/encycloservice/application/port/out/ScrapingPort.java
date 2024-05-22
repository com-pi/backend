package com.example.encycloservice.application.port.out;


import com.example.encycloservice.domain.PlantDetailResult;
import com.example.encycloservice.domain.SearchPlantResultList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${scraping-target.drfull.url}", name = "drfull")
public interface ScrapingPort {

    @GetMapping("/search/{keyword}")
    SearchPlantResultList searchPlant(@PathVariable String keyword);

    @GetMapping("/detail/{plantName}")
    PlantDetailResult plantDetail(@PathVariable String plantName);

}
