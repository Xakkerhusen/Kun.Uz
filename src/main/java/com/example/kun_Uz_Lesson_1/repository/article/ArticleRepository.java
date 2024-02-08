package com.example.kun_Uz_Lesson_1.repository.article;

import com.example.kun_Uz_Lesson_1.entity.ArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible=false where id=?1")
    Integer deleteArticle(String id);

    @Query("from ArticleEntity where id=?1 and visible=true ")
    Optional<ArticleEntity> findArticleById(String id);

    @Query("from ArticleEntity where id=?1")
    ArticleEntity find(String id);

    @Query("from ArticleEntity where visible=true order by createdDate desc ")
    List<ArticleEntity> findAllArticle();

    @Query("from ArticleEntity where id=?1 and status='PUBLISHED'")
    Optional<ArticleEntity> getArticle(String id);

    @Query("from ArticleEntity a where a.viewCount>0 order by a.viewCount desc limit ?1")
    List<ArticleEntity> mostReadArticles(Integer limit);

}
