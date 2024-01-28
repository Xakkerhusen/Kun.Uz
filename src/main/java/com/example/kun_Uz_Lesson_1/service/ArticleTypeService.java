package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleType;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.entity.ArticleTypeEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    private byte[] bytes;

    public CreatedArticleDTO create(CreatedArticleDTO articleType) {
        if (articleType.getOrderNumber() == null || articleType.getOrderNumber() <= 0) {
            throw new AppBadException("Article Type order number required ");
        }
        if (articleType.getNameEn() == null || articleType.getNameEn().trim().length() <= 1
                || articleType.getNameUz() == null || articleType.getNameUz().trim().length() <= 1
                || articleType.getNameRu() == null || articleType.getNameRu().trim().length() <= 1) {
            throw new AppBadException("Article Type Language required ");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(articleType.getOrderNumber());
        entity.setNameUz(articleType.getNameUz());
        entity.setNameRu(articleType.getNameRu());
        entity.setNameEn(articleType.getNameEn());
        articleTypeRepository.save(entity);

        return articleType;
    }

    public ArticleType update(Integer id, ArticleType dto) {
        ArticleTypeEntity entity = get(id);

        if (dto.getOrderNumber() != null) {
            entity.setOrderNumber(dto.getOrderNumber());
        }else {
            dto.setOrderNumber(entity.getOrderNumber());
        }
        if (dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }else {
            dto.setNameUz(entity.getNameUz());
        }
        if (dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }else {
            dto.setNameRu(entity.getNameRu());
        }
        if (dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }else {
            dto.setNameEn(entity.getNameEn());
        }
        entity.setUpdatedDate(LocalDateTime.now());

        articleTypeRepository.updateArticle(entity.getId(),entity.getNameUz(),entity.getNameRu(),entity.getNameEn(),entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        dto.setId(entity.getId());
        return dto;
    }

    public boolean delete(Integer id) {
        Integer effectiveRows = articleTypeRepository.deleteArticleTypeById(id);
        if (effectiveRows == 0) {
            throw new AppBadException("Article type not found");
        }
        return true;
    }

    public PageImpl<ArticleType> all(Pageable pageable) {
        Page<ArticleTypeEntity> all = articleTypeRepository.findAllArticle(pageable);
        List<ArticleTypeEntity> content = all.getContent();
        long totalElements = all.getTotalElements();
        List<ArticleType> listAll = new LinkedList<>();
        for (ArticleTypeEntity entity : content) {
            listAll.add(toDo(entity));
        }
        return new PageImpl<>(listAll, pageable, totalElements);
    }

    public List<ArticleType> allByLang(Language language) {

        List<ArticleTypeEntity> allArticleType = articleTypeRepository.findAllArticleType();
        List<ArticleType> listAll = new LinkedList<>();
        if (allArticleType.isEmpty()) {
            throw new AppBadException("Article is empty");
        }
        for (ArticleTypeEntity entity : allArticleType) {
            ArticleType dto = new ArticleType();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setCreatedDate(entity.getCreatedDate());
            switch (language){
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
                default -> dto.setName(entity.getNameEn());
            }
            listAll.add(dto);
        }
        return listAll;
    }

    private ArticleType toDo(ArticleTypeEntity entity) {
        ArticleType dto = new ArticleType();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        if (entity.getUpdatedDate() != null) {
        dto.setCreatedDate(entity.getUpdatedDate());
        }else {
        dto.setCreatedDate(entity.getCreatedDate());
        }
        dto.setVisible(entity.getVisible());
        return dto;
    }
    private ArticleTypeEntity get(Integer id) {
        return articleTypeRepository.findById(id).orElseThrow(() -> new AppBadException("Article not found"));
    }

}
