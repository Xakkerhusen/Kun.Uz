package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleDTO;
import com.example.kun_Uz_Lesson_1.entity.ArticleEntity;
import com.example.kun_Uz_Lesson_1.entity.CategoryEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDTO create(ArticleDTO dto, Integer profileId) {
        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionId());
        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryId());
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(profileId);

        ArticleEntity article = new ArticleEntity();

        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        article.setImagesId(dto.getImagesId());
        article.setRegion(region);
        article.setCategory(category);
        article.setModerator(profileEntity);
        articleRepository.save(article);


        return null;

    }
}
