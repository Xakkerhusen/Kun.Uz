package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.entity.ArticleNewsTypeEntity;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleNwsTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
public class ArticleNewsTypeService {
    @Autowired
    private ArticleNwsTypeRepository articleNwsTypeRepository;

    public void create(String articleId, List<Integer> typesIdList) {
        for (Integer typeId : typesIdList) {
            create(articleId, typeId);
        }
    }

    public void create(String articleId, Integer typesId) {
        ArticleNewsTypeEntity entity = new ArticleNewsTypeEntity();
        entity.setArticleId(articleId);
        entity.setNewsTypeId(typesId);
        articleNwsTypeRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> newList) {
        List<ArticleNewsTypeEntity> old = articleNwsTypeRepository.findByArticleId(articleId);
        if (old == null|| old.isEmpty()) {
            return;
        }
        for (ArticleNewsTypeEntity entity : old) {
            if (!newList.contains(entity.getNewsTypeId())) {
                articleNwsTypeRepository.deleteById(entity.getId());
            }
        }

        for (Integer typesId : newList) {
            Optional<ArticleNewsTypeEntity> optional = articleNwsTypeRepository.findTop1ByNewsTypeId(typesId);
            if (optional.isEmpty()){
                ArticleNewsTypeEntity entity=new ArticleNewsTypeEntity();
                entity.setArticleId(articleId);
                entity.setNewsTypeId(typesId);
                articleNwsTypeRepository.save(entity);
            }
        }

    }

    public ArticleNewsTypeEntity get(Integer id){
//        return articleNwsTypeRepository.findTop1ByNewsTypeId(id).orElseThrow(() -> new AppBadException("Type not found"));
        return articleNwsTypeRepository.findById(id).orElseThrow(() -> {
            log.warn("Type not found{}",id);
            return new AppBadException("Type not found");
        });
    }

}
