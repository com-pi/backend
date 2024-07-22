package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.domain.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommonArticleRepository extends JpaRepository<CommonArticleEntity, Long> {

    Page<CommonArticleEntity> findByDeletionYn(String deletionYn, Pageable pageable);

    Page<CommonArticleEntity> findByTypeAndDeletionYn(ArticleType type, String deletionYn, Pageable pageable);

    List<CommonArticleEntity> findByArticleIdIn(List<Long> articleIdList, Sort sort);

    Optional<CommonArticleEntity> findByArticleIdAndDeletionYn(Long articleId, String deletionYn);

    Optional<CommonArticleEntity> findByArticleId(Long articleId);

    @Query("""
        SELECT c 
        FROM CommonArticleEntity c 
        WHERE (c.title LIKE %:keyword% OR c.content LIKE %:keyword%)
            AND c.deletionYn = :deletionYn
    """)
    Page<CommonArticleEntity> searchArticleList(
            @Param("keyword") String keyword,
            @Param("deletionYn") String deletionYn, Pageable pageable
    );

    @Query("""
        SELECT c 
        FROM CommonArticleEntity c 
        WHERE (c.title LIKE %:keyword% OR c.content LIKE %:keyword%)
            AND c.type = :type 
            AND c.deletionYn = :deletionYn
    """)
    Page<CommonArticleEntity> searchArticleListWithType(
            @Param("keyword") String keyword,
            @Param("type") ArticleType type,
            @Param("deletionYn") String deletionYn, Pageable pageable
    );

    Page<CommonArticleEntity> findByMemberIdAndDeletionYn(Long memberId, String deletionYn, Pageable pageable);
}
