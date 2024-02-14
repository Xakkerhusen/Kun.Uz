package com.example.kun_Uz_Lesson_1.repository.article;

import com.example.kun_Uz_Lesson_1.entity.ArticleTagNameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleTagNameRepository extends CrudRepository<ArticleTagNameEntity,Integer> {
//    List<ArticleTagNameEntity> findAllByTagsIdOrderByCreatedDateDesc(Long tagId);
    List<ArticleTagNameEntity> findAllByTagNameIdOrderByCreatedDateDesc(Long tagId);

    List<ArticleTagNameEntity> findByArticleId(String articleID);

}
