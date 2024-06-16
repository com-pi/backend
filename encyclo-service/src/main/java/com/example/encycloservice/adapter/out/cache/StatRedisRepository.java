package com.example.encycloservice.adapter.out.cache;

import com.example.common.exception.InternalServerException;
import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class StatRedisRepository implements StatRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void recordRecentSearchKeyword(String keyword, long epochTimeStamp) {
        redisTemplate.opsForZSet().add(RedisKey.recentSearchKeyword, keyword, epochTimeStamp);
    }

    @Override
    public void recordRecentPlantDetails(RecentPlantDetailRecord plantSpeciesRecord, long epochTimeStamp) {
        try {
            redisTemplate.opsForZSet()
                    .add(RedisKey.recentPlantDetails, objectMapper.writeValueAsString(plantSpeciesRecord), epochTimeStamp);
        } catch (JsonProcessingException ignored) {
        }
    }

    @Override
    public RecentPlantDetailStat getRecentPlantDetails(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size - 1;

        Long totalElement_ = redisTemplate.opsForZSet().zCard(RedisKey.recentPlantDetails);
        int totalElement = totalElement_ == null ? 0 : totalElement_.intValue();
        int totalPage = (totalElement + size - 1) / size;

        Set<String> recordSet = redisTemplate.opsForZSet()
                .reverseRange(RedisKey.recentPlantDetails, start, end);
        List<String> recordList = recordSet == null ? new ArrayList<>() : recordSet.stream().toList();

        List<RecentPlantDetailStat.RecentPlantDetailRank> result = IntStream.range(0, recordList.size())
                .mapToObj(rank -> {
                    try {
                        return RecentPlantDetailStat.RecentPlantDetailRank.builder()
                                .rank(rank)
                                .recentPlantDetailRecord(objectMapper.readValue(recordList.get(rank), RecentPlantDetailRecord.class))
                                .build();
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException("파싱 에러 발생", e);
                    }
                }).toList();

        return RecentPlantDetailStat.builder()
                .totalPage(totalPage)
                .totalElement(totalElement)
                .results(result)
                .build();
    }

    @Override
    public RecentSearchKeywordStat getRecentSearchKeyword(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size - 1;

        Long totalElement_ = redisTemplate.opsForZSet().zCard(RedisKey.recentSearchKeyword);
        int totalElement = totalElement_ == null ? 0 : totalElement_.intValue();
        int totalPage = (totalElement + size - 1) / size;

        Set<String> keywordSet = redisTemplate.opsForZSet()
                .reverseRange(RedisKey.recentSearchKeyword, start, end);
        List<String> keywordList = keywordSet == null ? new ArrayList<>() : keywordSet.stream().toList();

        List<RecentSearchKeywordStat.RecentSearchKeywordRank> result = IntStream.range(0, keywordList.size())
                .mapToObj(rank ->
                        RecentSearchKeywordStat.RecentSearchKeywordRank.builder()
                                .rank(rank)
                                .keyword(keywordList.get(rank))
                                .build()
                ).toList();

        return RecentSearchKeywordStat.builder()
                .totalPage(totalPage)
                .totalElement(totalElement)
                .results(result)
                .build();
    }

}
