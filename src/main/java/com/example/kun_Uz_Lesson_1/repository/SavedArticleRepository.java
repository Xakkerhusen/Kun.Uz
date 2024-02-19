package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.SavedArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {

    Optional<SavedArticleEntity> findByArticleId(String articleId);

    @Transactional
    @Modifying
    @Query("delete from SavedArticleEntity where articleId=?1 and profileId=?2")
    Integer deleteSaveArticle(String articleId, Integer profileId);


    List<SavedArticleEntity>findAllByProfileId(Integer id);
}
