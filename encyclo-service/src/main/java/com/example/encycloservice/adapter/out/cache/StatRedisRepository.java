package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class StatRedisRepository implements StatRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final PopularityRecordRepository popularityRecordRepository;

    @Override
    public void recordRecentPlantDetails(Long plantId, long epochTimeStamp) {
        redisTemplate.opsForZSet().add(RedisKey.recentPlantDetails, plantId.toString(), epochTimeStamp);
    }

    @Override
    public void recordPopularPlant(Long plantId, String time, Long memberId) {
        String plantIdTimeKey = String.format("popularity:%s:%s", plantId, time);
        String plantIdKey = String.format("popularity:%s", plantId);

        createSetIfAbsent(plantIdTimeKey);
        createSetIfAbsent(plantIdKey);

        redisTemplate.opsForSet().add(plantIdTimeKey, memberId.toString());
        redisTemplate.opsForSet().add(plantIdKey, plantIdTimeKey);
    }

    private void createSetIfAbsent(String key) {
        int expirationTimeInDay = 5;
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            redisTemplate.opsForSet().add(key, "dummy");
            redisTemplate.expire(key, expirationTimeInDay, TimeUnit.DAYS);
            redisTemplate.opsForSet().remove(key, "dummy");
        }
    }

    @Override
    public RecentPlantDetailStatResult getRecentPlantDetails(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size - 1;

        Long totalElementLong = redisTemplate.opsForZSet().zCard(RedisKey.recentPlantDetails);
        int totalElement = totalElementLong == null ? 0 : totalElementLong.intValue();
        int totalPage = (totalElement + size - 1) / size;

        Set<String> recordSet = redisTemplate.opsForZSet().reverseRange(RedisKey.recentPlantDetails, start, end);
        List<String> recordList = recordSet == null ? new ArrayList<>() : recordSet.stream().toList();

        List<RecentPlantDetailStatResult.RecentPlantDetailRank> result = IntStream.range(0, recordList.size())
                .mapToObj(rank ->
                        RecentPlantDetailStatResult.RecentPlantDetailRank.builder()
                                .rank(rank)
                                .plantId(Long.valueOf(recordList.get(rank)))
                                .build()
                ).toList();

        return RecentPlantDetailStatResult.builder()
                .totalPage(totalPage)
                .totalElement(totalElement)
                .results(result)
                .build();
    }

    @Override
    public void updatePopularPlantStat(LocalDateTime now) {

        double epochNow = getEpochMillis(now);
        double epochPast = getEpochMillis(now.minusDays(3));

        Set<String> plantIdWithinPast = redisTemplate.opsForZSet()
                .reverseRangeByScore(RedisKey.recentPlantDetails, epochPast, epochNow);

        if(plantIdWithinPast == null) {
            plantIdWithinPast = new HashSet<>();
        }

        Map<String, Long> plantViewStat = new HashMap<>();

        for (String plantId : plantIdWithinPast){
            String plantIdKey = String.format("popularity:%s", plantId);
            Set<String> plantIdTimeKeys = redisTemplate.opsForSet().members(plantIdKey);
            if(plantIdTimeKeys == null) {
                plantIdTimeKeys = new HashSet<>();
            }
            Long views = 0L;
            for (String plantIdTimeKey : plantIdTimeKeys){
                views += redisTemplate.opsForSet().size(plantIdTimeKey);
            }
            plantViewStat.put(plantId, views);
        }

        popularityRecordRepository.updateRecord(plantViewStat);
    }

    @Override
    public PopularPlantStatResult getPopularPlantList() {
        return popularityRecordRepository.getPopularPlantStat();
    }

    private double getEpochMillis(LocalDateTime time){
        return (double) time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
