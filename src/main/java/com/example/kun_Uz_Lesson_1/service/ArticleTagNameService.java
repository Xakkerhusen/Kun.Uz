package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.entity.ArticleTagNameEntity;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleTagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagNameService {
    @Autowired
    private ArticleTagNameRepository articleTagNameRepository;

    public void create(String articleId, List<Long> typesIdList) {
        for (Long typeId : typesIdList) {
            create(articleId, typeId);
        }
    }

    public void create(String articleId, Long tagId) {
        ArticleTagNameEntity entity = new ArticleTagNameEntity();
        entity.setArticleId(articleId);
        entity.setTagNameId(tagId);
        articleTagNameRepository.save(entity);
    }
}
