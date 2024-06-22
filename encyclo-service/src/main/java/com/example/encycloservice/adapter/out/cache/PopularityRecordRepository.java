package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

@Repository
public class PopularityRecordRepository {

    private String recordInfo = "";
    private NavigableMap<Long, String> sortedPlantView = new TreeMap<>();

    public void updateRecord(TreeMap<Long, String> newRecord){
        recordInfo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 HH시"));
        sortedPlantView = newRecord.descendingMap();
    }

    // Todo 수정 필요
    public PopularPlantStat getPopularPlantStat(int size){
        List<PopularPlantStat.PlantRank> plantRankList = new ArrayList<>();
        int rank = 1;
        for (var entry : sortedPlantView.entrySet()){
            plantRankList.add(new PopularPlantStat.PlantRank(rank++, entry.getValue()));
        }
        return new PopularPlantStat(recordInfo, plantRankList);
    }

}
