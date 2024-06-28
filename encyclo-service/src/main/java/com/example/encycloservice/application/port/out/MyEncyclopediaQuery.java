package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.MyEncyclopedia;

import java.util.List;
import java.util.Optional;

public interface MyEncyclopediaQuery {

    List<MyEncyclopedia> getMyEncyclopediaList(Long memberId);
    Optional<MyEncyclopedia> findById(Long id);
    Optional<MyEncyclopedia> findEncyclopediaWithContentById(Long myEncyclopediaId);
}
