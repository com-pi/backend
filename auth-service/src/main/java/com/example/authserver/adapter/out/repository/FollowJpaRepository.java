package com.example.authserver.adapter.out.repository;

import com.example.authserver.adapter.out.entity.FollowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    @Query("DELETE FROM FOLLOW f WHERE f.followee.id = :followeeId AND f.follower.id = :followerId")
    @Modifying
    void deleteByFolloweeIdAnAndFollowerId(Long followeeId, Long followerId);

    @Query("SELECT f FROM FOLLOW f JOIN FETCH f.follower WHERE f.followee.id = :memberId ")
    Page<FollowEntity> findFollowerByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT f FROM FOLLOW f JOIN FETCH f.followee WHERE f.follower.id = :memberId")
    Page<FollowEntity> findFolloweeByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT EXISTS (SELECT 1 FROM FOLLOW f WHERE f.follower.id = :followerId AND f.followee.id = :followeeId)")
    Boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    @Query("SELECT COUNT(f) FROM FOLLOW f WHERE f.follower.id = :memberId")
    Integer countByFollowerId(Long memberId);

    @Query("SELECT COUNT(f) FROM FOLLOW f WHERE f.followee.id = :memberId")
    Integer countByFolloweeId(Long memberId);

}
