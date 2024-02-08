package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.example.kun_Uz_Lesson_1.dto.article.ArticleFullInfoDTO;
import com.example.kun_Uz_Lesson_1.dto.article.ArticleShortInfoDTO;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.entity.*;
import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleNwsTypeRepository;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleNewsTypeService articleNewsTypeService;
    @Autowired
    private ArticleNwsTypeRepository articleNwsTypeRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;

    public CreatedArticleDTO create(CreatedArticleDTO dto, Integer profileId) {

        ArticleEntity article = new ArticleEntity();
        article.setTitleUz(dto.getTitleUz());
        article.setTitleRu(dto.getTitleRu());
        article.setTitleEn(dto.getTitleEn());
        article.setDescriptionUz(dto.getDescriptionUz());
        article.setDescriptionRu(dto.getDescriptionRu());
        article.setDescriptionEn(dto.getDescriptionEn());
        article.setContentUz(dto.getContentUz());
        article.setContentRu(dto.getContentRu());
        article.setContentEn(dto.getContentEn());
        article.setImageId(dto.getImageId());
        article.setRegionId(dto.getRegionId());
        article.setCategoryId(dto.getCategoryId());
        article.setModeratorId(profileId);
        articleRepository.save(article);

        articleNewsTypeService.create(article.getId(), dto.getNewsType());
        return dto;
    }

    public CreatedArticleDTO update(CreatedArticleDTO dto, String id, Integer moderatorId) {
        ArticleEntity entity = get(id);
//        ArticleEntity article = new ArticleEntity();
//        entity.setTitle(dto.getTitle());
//        entity.setDescription(dto.getDescription());
//        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(moderatorId);
        articleRepository.save(entity);
        articleNewsTypeService.merge(entity.getId(), dto.getNewsType());
        return dto;
    }

    public Boolean delete(String id) {
        Integer effectiveRows = articleRepository.deleteArticle(id);
        if (effectiveRows == 0) {
            log.warn("Article not found {}", id);
            throw new AppBadException("Article not found");
        }

        return true;
    }


    public Boolean changeStatusById(String id, Integer publisherId) {
        ArticleEntity entity = get(id);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisherId(publisherId);
        entity.setPublishedDate(LocalDateTime.now());
        articleRepository.save(entity);
        return true;
    }

    public List<ArticleShortInfoDTO> getLastFiveArticleByTypes(Integer id, Integer limit) {
        List<ArticleNewsTypeEntity> articles = getArticles(id, limit);
        List<ArticleEntity> list = new LinkedList<>();
        for (ArticleNewsTypeEntity articleId : articles) {
            list.add(articleRepository.find(articleId.getArticleId()));
        }

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : list) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public List<ArticleShortInfoDTO> listOfArticlesExceptGivenIds(String[] id, Integer limit) {
        List<ArticleEntity> allArticle = articleRepository.findAllArticle();
        List<ArticleEntity> entityList = new LinkedList<>();

        for (ArticleEntity entity : allArticle) {
            String id1 = entity.getId();
            int count = 0;
            for (String s : id) {
                if (id1.equals(s)) {
                    count++;
                }
            }
            if (entityList.size() >= limit) {
                break;
            }
            if (count == 0) {
                entityList.add(entity);
            }
        }
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public ArticleFullInfoDTO getArticleByIdAndLanguage(String id, Language language) {
        ArticleEntity entity = getArticle(id);
        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        RegionEntity regionEntity = regionService.get(entity.getRegionId());
        CategoryEntity categoryEntity = categoryService.get(entity.getCategoryId());
        /* Region dto = new Region();
                dto.setId(entity.getId());
                switch (language) {
                    case UZ -> dto.setName(entity.getNameUz());
                    case RU -> dto.setName(entity.getNameRu());
                    default -> dto.setName(entity.getNameEn());
                }
        id(uuid),title,description,content,shared_count,
        region(key,name),category(key,name),published_date,view_count,like_count,
        tagList(name)*/
        dto.setId(entity.getId());
        dto.setShareCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());
        switch (language) {
            case UZ -> dto.setTitle(entity.getTitleUz());
            case RU -> dto.setTitle(entity.getTitleRu());
            default -> dto.setTitle(entity.getTitleEn());
        }
        switch (language) {
            case UZ -> dto.setDescription(entity.getDescriptionUz());
            case RU -> dto.setDescription(entity.getDescriptionRu());
            default -> dto.setDescription(entity.getDescriptionEn());
        }
        switch (language) {
            case UZ -> dto.setContent(entity.getContentUz());
            case RU -> dto.setContent(entity.getContentRu());
            default -> dto.setContent(entity.getContentEn());
        }
        switch (language) {
            case UZ -> dto.setRegion(regionEntity.getNameUz());
            case RU -> dto.setRegion(regionEntity.getNameRu());
            default -> dto.setRegion(regionEntity.getNameEn());
        }
        switch (language) {
            case UZ -> dto.setCategory(categoryEntity.getNameUz());
            case RU -> dto.setCategory(categoryEntity.getNameRu());
            default -> dto.setCategory(categoryEntity.getNameEn());
        }
        return dto;
    }
    public List<ArticleShortInfoDTO> mostReadArticles(Integer limit) {
        List<ArticleEntity> entities = articleRepository.mostReadArticles(limit);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entities) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }
    public List<ArticleShortInfoDTO> getArticleByNewsTypeIdAndRegionId(Integer typeId, Integer limit, Integer regionId) {
        List<ArticleNewsTypeEntity> articles = getArticles(typeId, limit);

        return null;
    }


    public Integer increaseArticleViewCount(String id) {
        ArticleEntity entity = getArticle(id);
        entity.setViewCount(entity.getViewCount() + 1);
        articleRepository.save(entity);
        return entity.getViewCount();
    }

    public Integer increaseArticleShareCount(String id) {
        ArticleEntity entity = getArticle(id);
        entity.setSharedCount(entity.getSharedCount() + 1);
        articleRepository.save(entity);
        return entity.getSharedCount();
    }
    private ArticleShortInfoDTO toDo(ArticleEntity entity) {
        AttachEntity attachEntity = attachService.get(entity.getImageId());
        AttachDTO attachDTO = attachService.toDTO(attachEntity);

        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setImage(attachDTO);
        return dto;
    }

    private ArticleEntity get(String id) {
//        return articleRepository.findArticleById(id).orElseThrow(() -> new AppBadException("Article not found"));
        return articleRepository.findById(id).orElseThrow(() -> {
            log.warn("Article not found{}", id);
            return new AppBadException("Article not found");
        });
    }
    private ArticleEntity getArticle(String id) {
        return articleRepository.getArticle(id).orElseThrow(() -> {
            log.warn("Article not found{}",id);
            return new AppBadException("Article not found");
        });
    }

    private List<ArticleNewsTypeEntity> getArticles(Integer id,Integer limit) {
        /*List<String> listArticleId = articleNwsTypeRepository.findByNewsTypeIdOrderByCreatedDateDesc(id, limit);*/
        List<ArticleNewsTypeEntity> articleIds = articleNwsTypeRepository.findByNewsTypeIdOrderByCreatedDateDesc(id, limit);
        if (articleIds.isEmpty()){
            log.warn("New Type not found{}",id);
            throw new AppBadException("New Type not found");
        }
        return articleIds;
    }


}
