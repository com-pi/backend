package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class StatRedisRepository implements StatRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final PopularityRecordRepository popularityRecordRepository;

    private final DateTimeFormatter dateTimeHour = DateTimeFormatter.ofPattern("MMddHH");
    final int expirationTimeInDay = 1;

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
    public void recordRecentPlantDetails(Long plantId, long currentEpochSecond) {
        redisTemplate.opsForZSet().add(RedisKey.recentPlantDetails, plantId.toString(), currentEpochSecond);
    }

    @Override
    public void recordPopularPlant(Long plantId, LocalDateTime time, Long memberId) {
        final String plantIdTimeKey = String.format("%s:%s:%s", RedisKey.popularity, plantId, time.format(dateTimeHour));
        final String plantIdKey = RedisKey.getPopularityPlantIdKey(plantId);

        // 특정 식물에 특정 시간 구간에서 처음 조회가 발생한 경우
        if(Boolean.FALSE.equals(redisTemplate.hasKey(plantIdTimeKey))) {
            redisTemplate.opsForSet().add(plantIdTimeKey, "dummy");
            redisTemplate.expire(plantIdTimeKey, expirationTimeInDay, TimeUnit.DAYS);
            redisTemplate.opsForSet().remove(plantIdTimeKey, "dummy");
            redisTemplate.opsForZSet().add(plantIdKey, plantIdTimeKey, time.atZone(ZoneId.systemDefault()).toEpochSecond());
        }

        redisTemplate.opsForSet().add(plantIdTimeKey, memberId.toString());
    }


    @Override
    public void updatePopularPlantStat(LocalDateTime now) {
        long nowSecond = now.truncatedTo(ChronoUnit.HOURS)
                .atZone(ZoneId.systemDefault()).toEpochSecond();
        long pastSecond = now.truncatedTo(ChronoUnit.HOURS).minusDays(expirationTimeInDay)
                .atZone(ZoneId.systemDefault()).toEpochSecond();

        redisTemplate.opsForZSet()
                .removeRangeByScore(RedisKey.recentPlantDetails, 0, nowSecond - 1);

        Set<String> plantIdWithinPast = redisTemplate.opsForZSet()
                .rangeByScore(RedisKey.recentPlantDetails, pastSecond, nowSecond);

        if(plantIdWithinPast == null || plantIdWithinPast.isEmpty()) {
            return;
        }

        Map<String, Long> plantViewStat = new HashMap<>();
        for (String plantId : plantIdWithinPast){
            String plantIdKey = RedisKey.getPopularityPlantIdKey(plantId);
            Set<String> plantIdTimeKeys = redisTemplate.opsForSet().members(plantIdKey);

            if(plantIdTimeKeys == null || plantIdTimeKeys.isEmpty()) {
                continue;
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

}
