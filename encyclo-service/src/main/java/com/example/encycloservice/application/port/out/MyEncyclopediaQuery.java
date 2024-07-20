package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.out.persistence.entity.EncyclopediaPlantEntity;
import com.example.encycloservice.domain.MyEncyclopedia;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MyEncyclopediaQuery {

    List<MyEncyclopedia> getMyEncyclopediaList(Long memberId);
    Optional<MyEncyclopedia> findById(Long id);
    Optional<MyEncyclopedia> findEncyclopediaWithContentById(Long myEncyclopediaId);
    Optional<MyEncyclopedia> findEncyclopedia(Long fromEncyclopediaId);
    Page<EncyclopediaPlantEntity> getPlantListByEncyclopediaId(Long myEncyclopediaId, Integer page, Integer size);

}
