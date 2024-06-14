package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.in.web.response.MyPlantDetailResponse;
import com.example.myplant.adapter.out.persistence.entity.MyPlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPlantRepository extends JpaRepository<MyPlantEntity, Long> {
    List<MyPlantEntity> findByMemberIdAndDeletionYn(Long memberId, String deleteionYN);

    Optional<MyPlantEntity> findByMyPlantIdAndDeletionYn(Long myPlantId, String n);
}
