package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.article.CreateArticleLikeDTO;
import com.example.kun_Uz_Lesson_1.entity.ArticleLikeEntity;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleService articleService;

    public Object create(String articleId, Integer profileId, CreateArticleLikeDTO dto) {
        articleService.get(articleId);

        Optional<ArticleLikeEntity> optional = articleLikeRepository.findTop1ByArticleId(articleId);

        ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();

        if (optional.isEmpty()) {
            articleLikeEntity.setArticleId(articleId);
            articleLikeEntity.setProfileId(profileId);
            articleLikeEntity.setStatus(dto.getStatus());
            articleLikeRepository.save(articleLikeEntity);
            return "SUCCESS save";
        }
        ArticleLikeEntity entity = optional.get();
        if (entity.getStatus().equals(dto.getStatus()) &&
                entity.getArticleId().equals(articleId) && entity.getProfileId().equals(profileId)) {
            articleLikeRepository.deleteById(entity.getId());
            return "SUCCESS delete";
        } else if (entity.getArticleId().equals(articleId) && !entity.getProfileId().equals(profileId)) {
            articleLikeEntity.setArticleId(articleId);
            articleLikeEntity.setProfileId(profileId);
            articleLikeEntity.setStatus(dto.getStatus());
            articleLikeRepository.save(articleLikeEntity);
            return "SUCCESS save";
        } else {
            return articleLikeRepository.update(entity.getId(), dto.getStatus(), LocalDateTime.now()) != 0 ? "update" : "articleId not found";
        }

    }
}
