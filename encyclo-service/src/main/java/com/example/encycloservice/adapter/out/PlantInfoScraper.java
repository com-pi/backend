package com.example.encycloservice.adapter.out;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${scraping-target.drfull.url}", name = "drfull")
public interface PlantInfoScraper {

    @GetMapping("/search/{keyword}")
    SearchPlantResultList searchPlant(@PathVariable String keyword);

    @GetMapping("/detail/{plantName}")
    PlantDetailResult plantDetail(@PathVariable String plantName);

}
