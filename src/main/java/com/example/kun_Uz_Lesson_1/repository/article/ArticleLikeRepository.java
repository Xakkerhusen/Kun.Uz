package com.example.kun_Uz_Lesson_1.repository.article;

import com.example.kun_Uz_Lesson_1.entity.ArticleLikeEntity;
import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ArticleLikeEntity set status=?2,updatedDate=?3 where id=?1")
    Integer update(Integer id, LikeStatus status, LocalDateTime now);

    @Query("from ArticleLikeEntity where articleId=?1 and profileId=?2 ")
    Optional<ArticleLikeEntity> findTop1ByArticleId(String articleId, Integer profileId);
    @Query("from ArticleLikeEntity where status='LIKE' and articleId=?1")
    List<ArticleLikeEntity> countByArticleId(String id);
}
