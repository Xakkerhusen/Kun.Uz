package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.article.CreatedNewsTypeDTO;
import com.example.kun_Uz_Lesson_1.dto.article.NewsType;
import com.example.kun_Uz_Lesson_1.entity.NewsTypeEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.NewsTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
public class NewsTypeService {
    @Autowired
    private NewsTypeRepository articleTypeRepository;

    public CreatedNewsTypeDTO create(CreatedNewsTypeDTO articleType) {
        NewsTypeEntity entity = new NewsTypeEntity();
        entity.setOrderNumber(articleType.getOrderNumber());
        entity.setNameUz(articleType.getNameUz());
        entity.setNameRu(articleType.getNameRu());
        entity.setNameEn(articleType.getNameEn());
        articleTypeRepository.save(entity);
        return articleType;
    }

    public NewsType update(Integer id, NewsType dto) {
        NewsTypeEntity entity = get(id);

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
            log.warn("Article type not found{}",id);
            throw new AppBadException("Article type not found");
        }
        return true;
    }

    public PageImpl<NewsType> all(Pageable pageable) {
        Page<NewsTypeEntity> all = articleTypeRepository.findAllArticle(pageable);
        List<NewsTypeEntity> content = all.getContent();
        long totalElements = all.getTotalElements();
        List<NewsType> listAll = new LinkedList<>();
        for (NewsTypeEntity entity : content) {
            listAll.add(toDo(entity));
        }
        return new PageImpl<>(listAll, pageable, totalElements);
    }

    public List<NewsType> allByLang(Language language) {
        List<NewsTypeEntity> allArticleType = articleTypeRepository.findAllArticleType();
        List<NewsType> listAll = new LinkedList<>();
        if (allArticleType.isEmpty()) {
            log.warn("Article type not found{}",language);
            throw new AppBadException("Article is empty");
        }
        for (NewsTypeEntity entity : allArticleType) {
            NewsType dto = new NewsType();
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

    private NewsType toDo(NewsTypeEntity entity) {
        NewsType dto = new NewsType();
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
    public NewsTypeEntity get(Integer id) {
//        return articleTypeRepository.findById(id).orElseThrow(() -> new AppBadException("Article not found"));
        return articleTypeRepository.findById(id).orElseThrow(() -> {
            log.warn("Article not found{}",id);
            return new AppBadException("Article not found");
        });
    }

}
