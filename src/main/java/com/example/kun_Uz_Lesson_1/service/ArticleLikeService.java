package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.article.CreateArticleLikeDTO;
import com.example.kun_Uz_Lesson_1.entity.ArticleLikeEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleLikeRepository;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleRepository;
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
    @Autowired
    private ArticleRepository articleRepository;

    public Object create(String articleId, Integer profileId, CreateArticleLikeDTO dto, Language language) {
        articleService.getArticle(articleId,language);

        Optional<ArticleLikeEntity> optional = articleLikeRepository.findTop1ByArticleId(articleId,profileId);

        ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();

        if (optional.isEmpty()) {
            articleLikeEntity.setArticleId(articleId);
            articleLikeEntity.setProfileId(profileId);
            articleLikeEntity.setStatus(dto.getStatus());
            articleLikeRepository.save(articleLikeEntity);
            return "SUCCESS save";
        }
        ArticleLikeEntity entity = optional.get();
        LikeStatus status = entity.getStatus();
        if (status.equals(dto.getStatus())) {
//            return articleLikeRepository.deleteLikeById(entity.getId())>0?"SUCCESS delete":"o'chirilmadi";
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

    public Integer getLikeCount(String id) {
        return articleLikeRepository.countByArticleId(id).size();
    }
}
