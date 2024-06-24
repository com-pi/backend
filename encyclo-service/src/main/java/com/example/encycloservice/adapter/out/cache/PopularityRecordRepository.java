package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PopularityRecordRepository {

    private String referenceTime = "";
    private List<PlantRank> plantViewRank = new ArrayList<>();

    public void updateRecord(Map<String, Long> newRecord){
        referenceTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 HH시"));
        AtomicInteger rank = new AtomicInteger(1);
        plantViewRank = newRecord.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) // 키 기준으로 내림차순 정렬
                .limit(10)
                .map(entry -> new PlantRank(rank.getAndIncrement(), entry.getKey()))
                .toList();
    }

    public PopularPlantStat getPopularPlantStat(){
        return new PopularPlantStat(referenceTime, plantViewRank);
    }

    public record PlantRank(
            int rank,
            String plantId
    ){}

}
