package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.CreateSavedArticleDTO;
import com.example.kun_Uz_Lesson_1.dto.SaveArticleFullInfoDTO;
import com.example.kun_Uz_Lesson_1.entity.SavedArticleEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private ArticleService articleService;

    public Object create(CreateSavedArticleDTO dto, Integer profileId, Language language) {
        articleService.getArticle(dto.getArticleId(), language);
        Optional<SavedArticleEntity> optional = savedArticleRepository.findByArticleId(dto.getArticleId());
        if (optional.isPresent()) {
            throw new AppBadException(resourceBundleService.getMessage("this.article.has.been.saved.before",language));
        }
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        savedArticleRepository.save(entity);
        return resourceBundleService.getMessage("article.saved",language);
    }

    public Object delete(String articleId, Integer profileId, Language language) {
        return savedArticleRepository.deleteSaveArticle(articleId,profileId)!=0
                ?true: resourceBundleService.getMessage("no.such.article.was.found.among.the.saved.ones",language);
    }

    public List<SaveArticleFullInfoDTO> getAll(Language language, Integer profileId) {
        Iterable<SavedArticleEntity> all = savedArticleRepository.findAllByProfileId(profileId);
        List<SaveArticleFullInfoDTO>dtoList=new LinkedList<>();
        for (SavedArticleEntity entity : all) {
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    private SaveArticleFullInfoDTO toDo(SavedArticleEntity entity, Language language) {
        SaveArticleFullInfoDTO dto = new SaveArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setArticle(articleService.getForSavedArticle(entity.getArticleId(),language));
        return dto;
    }
}
