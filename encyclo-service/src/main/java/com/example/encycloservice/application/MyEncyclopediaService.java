package com.example.encycloservice.application;

import com.example.common.exception.CommonException;
import com.example.common.exception.NotFoundException;
import com.example.encycloservice.aop.filter.PassportHolder;
import com.example.encycloservice.application.port.in.MyEncyclopediaUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.MyEncyclopediaCommand;
import com.example.encycloservice.application.port.out.MyEncyclopediaQuery;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MyEncyclopediaService implements MyEncyclopediaUseCase {

    private final MyEncyclopediaCommand myEncyclopediaCommand;
    private final MyEncyclopediaQuery myEncyclopediaQuery;
    private final EncyclopediaQuery encyclopediaQuery;

    @Override
    public List<MyEncyclopedia> myEncyclopediaList(Long memberId) {
        return myEncyclopediaQuery.getMyEncyclopediaList(memberId);
    }

    @Override
    public MyEncyclopedia getMyEncyclopediaDetail(Long myEncyclopediaId) {
        return myEncyclopediaQuery.findEncyclopediaWithContentById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
    }

    @Override
    public void createEncyclopedia(MyEncyclopediaCreate myEncyclopediaCreate) {
        MyEncyclopedia myEncyclopedia = MyEncyclopedia.create(myEncyclopediaCreate);
        myEncyclopediaCommand.createEncyclopedia(myEncyclopedia);
    }

    @Override
    @Transactional
    public void addPlantsToEncyclopedia(List<Long> plantSpeciesIds, Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());


        List<CompletableFuture<Void>> futures = plantSpeciesIds.stream()
                .map(plantSpeciesId -> CompletableFuture.runAsync(() -> {
                    PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
                    if (plantSpecies == null) {
                        throw new NotFoundException("식물을 찾을 수 없습니다.");
                    }
                    try {
                        myEncyclopediaCommand.addPlantsToEncyclopedia(plantSpecies, myEncyclopedia);
                    } catch (DataIntegrityViolationException ignored) {
                        // 내 도감에 동일한 식물이 들어가 있을 때, 이를 무시합니다.
                    }
                })).toList();

        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            all.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CommonException("식물을 찾을 수 없습니다.", 404, e);
        }
    }

    @Override
    public void removePlantsFromEncyclopedia(List<Long> plantSpeciesIds, Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        List<CompletableFuture<Void>> futures = plantSpeciesIds.stream()
                .map(plantSpeciesId -> CompletableFuture.runAsync(() -> {
                    PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
                    if (plantSpecies == null) {
                        throw new NotFoundException("식물을 찾을 수 없습니다.");
                    }
                    myEncyclopediaCommand.removePlantFromEncyclopedia(plantSpecies, myEncyclopedia);
                })).toList();
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            all.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CommonException("식물을 찾을 수 없습니다.", 404, e);
        }
    }

}