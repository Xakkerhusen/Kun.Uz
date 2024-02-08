package com.example.kun_Uz_Lesson_1.repository.article;

import com.example.kun_Uz_Lesson_1.entity.ArticleNewsTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleNwsTypeRepository extends CrudRepository<ArticleNewsTypeEntity,Integer> {
//    @Query("select ant.newsTypeId from ArticleNewsTypeEntity ant where ant.articleId=?1")
//    @Query(value = "select ant.news_type_id from article_news_type ant where ant.article_id=?1 and  ant.status='NOT_PUBLISHED'",nativeQuery = true)
    List<ArticleNewsTypeEntity> findByArticleId(String articleId);

    Optional<ArticleNewsTypeEntity> findTop1ByNewsTypeId(Integer typesId);

    @Query(value = "select * from article_news_type ant where ant.news_type_id=?1 order by ant.created_date desc limit ?2",nativeQuery = true)
    List<ArticleNewsTypeEntity> findByNewsTypeIdOrderByCreatedDateDesc(Integer id, Integer limit);

}
