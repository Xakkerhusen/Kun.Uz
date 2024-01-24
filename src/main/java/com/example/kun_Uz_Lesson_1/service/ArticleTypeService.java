package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.ArticleType;
import com.example.kun_Uz_Lesson_1.entity.ArticleTypeEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    private byte[] bytes;

    public ArticleType create(ArticleType articleType) {
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

        articleType.setId(entity.getId());
        articleType.setCreatedDate(entity.getCreatedDate());
        articleType.setVisible(entity.getVisible());
        return articleType;
    }

    public ArticleType update(Integer id, ArticleType dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("ArticleType not found");
        }
        ArticleTypeEntity entity = optional.get();
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
        articleTypeRepository.updateArticle(entity.getId(),entity.getNameUz(),entity.getNameRu(),entity.getNameEn(),entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
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

    public List<ArticleType> allByLang(String language) {
        if (!language.toUpperCase().equals(String.valueOf(Language.EN))&&
        !language.toUpperCase().equals(String.valueOf(Language.UZ))&&
                !language.toUpperCase().equals(String.valueOf(Language.RU))){
            throw new AppBadException("Wrong language");
        }
        Language language1 = Language.valueOf(language.toUpperCase());
        List<ArticleTypeEntity> allArticleType = articleTypeRepository.findAllArticleType();
        List<ArticleType> listAll = new LinkedList<>();
        if (allArticleType.isEmpty()) {
            throw new AppBadException("ERROR!!!");
        }
        for (ArticleTypeEntity entity : allArticleType) {
            listAll.add(toDo2(entity,language1));
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
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    private ArticleType toDo2(ArticleTypeEntity entity, Language language1) {
        ArticleType dto = new ArticleType();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        if (language1.equals(Language.UZ)){
        dto.setNameUz(entity.getNameUz());
        }else if (language1.equals(Language.EN)){
        dto.setNameEn(entity.getNameEn());
        }else if (language1.equals(Language.RU)){
        dto.setNameRu(entity.getNameRu());
        }
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


}
