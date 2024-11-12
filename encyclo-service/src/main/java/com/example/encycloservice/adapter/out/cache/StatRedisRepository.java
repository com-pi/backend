package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.*;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class StatRedisRepository implements StatRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final PopularityRecordRepository popularityRecordRepository;
    private final ZoneOffset offset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();

    @Override
    public void recordRecentPlantDetails(Long plantId, long epochTimeStamp) {
        redisTemplate.opsForZSet().add(RedisKey.recentPlantDetails, plantId.toString(), epochTimeStamp);
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
    public void recordPopularPlant(Long plantId, Long memberId, LocalDateTime now) {
        LocalDateTime currentHour = now.withMinute(0).withSecond(0).withNano(0);
        long epochSecondOfCurrentHour = currentHour.toEpochSecond(offset);

        final String plantIdTimeKey = String.format("%s:%s:%s", RedisKey.popularity, plantId, epochSecondOfCurrentHour);
        final String plantIdKey = String.format("%s:%s", RedisKey.popularity, plantId);

        // TTL 설정
        if (Boolean.FALSE.equals(redisTemplate.hasKey(plantIdTimeKey))) {
            redisTemplate.opsForHyperLogLog().add(plantIdTimeKey, memberId.toString());
            redisTemplate.expire(plantIdTimeKey, Duration.ofHours(25));
        } else {
            redisTemplate.opsForHyperLogLog().add(plantIdTimeKey, memberId.toString());
        }

        redisTemplate.opsForZSet().add(plantIdKey, plantIdTimeKey, epochSecondOfCurrentHour);
    }

    @Override
    public void updatePopularPlantStat(LocalDateTime now) {
        double epochCurrentHour = getEpochMillis(now.minusHours(1).withMinute(0).withSecond(0).withNano(0));
        double epochPastHour = getEpochMillis(now.minusHours(24).withMinute(0).withSecond(0).withNano(0));

        Set<String> plantIdWithinTimePeriod = redisTemplate.opsForZSet()
                .reverseRangeByScore(RedisKey.recentPlantDetails, epochPastHour, epochCurrentHour);

        Map<String, Long> plantViewStat = new HashMap<>();
        for (String plantId : plantIdWithinTimePeriod){
            String plantIdKey = String.format("%s:%s", RedisKey.popularity, plantId);
            redisTemplate.opsForZSet().removeRangeByScore(plantIdKey, Double.NEGATIVE_INFINITY, epochPastHour);
            Set<String> plantIdTimeKeys = redisTemplate.opsForZSet()
                    .rangeByScore(plantIdKey, epochPastHour, epochCurrentHour);

            long views = 0L;
            for (String plantIdTimeKey : plantIdTimeKeys){
                views += redisTemplate.opsForHyperLogLog().size(plantIdTimeKey);
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
