package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.dto.article.*;
import com.example.kun_Uz_Lesson_1.dto.category.Category;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.entity.*;
import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.CustomRepository;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleNwsTypeRepository;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleRepository;
import com.example.kun_Uz_Lesson_1.repository.article.ArticleTagNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleNwsTypeRepository articleNwsTypeRepository;
    @Autowired
    private ArticleTagNameRepository articleTagNameRepository;
    @Autowired
    private ArticleNewsTypeService articleNewsTypeService;
    @Autowired
    private ArticleTagNameService articleTagNameService;
    @Autowired
    @Lazy
    private ArticleLikeService articleLikeService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagNameService tagNameService;
    @Autowired
    private ResourceBundleService resourceBundleService;

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
        articleTagNameService.create(article.getId(), dto.getTagName());
        return dto;
    }

    public CreatedArticleDTO update(CreatedArticleDTO dto, String id, Integer moderatorId, Language language) {
        ArticleEntity entity = get(id,language);
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(moderatorId);
        articleRepository.save(entity);
        articleNewsTypeService.merge(entity.getId(), dto.getNewsType());
        return dto;
    }

    public Boolean delete(String id, Language language) {
        Integer effectiveRows = articleRepository.deleteArticle(id);
        if (effectiveRows == 0) {
            log.warn("Article not found {}", id);
            throw new AppBadException(resourceBundleService.getMessage("article.not.found",language));
        }

        return true;
    }


    public Boolean changeStatusById(String id, Integer publisherId, Language language) {
        ArticleEntity entity = get(id,language);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisherId(publisherId);
        entity.setPublishedDate(LocalDateTime.now());
        articleRepository.save(entity);
        return true;
    }

    public List<ArticleShortInfoDTO> getLastFiveArticleByTypes(Integer id, Integer limit, Language language) {
        List<ArticleNewsTypeEntity> articles = getArticles(id, limit,language);
        List<ArticleEntity> list = new LinkedList<>();
        for (ArticleNewsTypeEntity articleId : articles) {
            list.add(articleRepository.find(articleId.getArticleId()));
        }

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : list) {
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    public List<ArticleShortInfoDTO> listOfArticlesExceptGivenIds(String[] id, Integer limit, Language language) {
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
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    //8
    public ArticleFullInfoDTO getArticleByIdAndLanguage(String id, Language language) {
        ArticleEntity entity = get(id,language);

        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        Region region = new Region();
        Category category = new Category();
        List<String> tagNameDTOList = new LinkedList<>();

        RegionEntity regionEntity = regionService.get(entity.getRegionId(),language);
        CategoryEntity categoryEntity = categoryService.get(entity.getCategoryId(),language);
        List<ArticleTagNameEntity> articleTagNameEntity = articleTagNameRepository.findByArticleId(id);
        if (articleTagNameEntity.isEmpty()) {
            log.warn("No articles with this tag name were found{}", id);
            throw new AppBadException(resourceBundleService.getMessage("no.articles.with.this.tag.name.were.found",language));
        }
        for (ArticleTagNameEntity tagNameEntity : articleTagNameEntity) {
            if (tagNameService.get(tagNameEntity.getTagNameId(),language) != null) {
                tagNameDTOList.add(tagNameEntity.getTagName().getTagName());
            }
        }

        region.setId(regionEntity.getId());
        category.setId(categoryEntity.getId());

        dto.setId(entity.getId());
        dto.setShareCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());

        switch (language) {
            case UZ -> {
                dto.setTitle(entity.getTitleUz());
                dto.setDescription(entity.getDescriptionUz());
                dto.setContent(entity.getContentUz());
                region.setName(regionEntity.getNameUz());
                category.setName(categoryEntity.getNameUz());
            }
            case RU -> {
                dto.setTitle(entity.getTitleRu());
                dto.setDescription(entity.getDescriptionRu());
                dto.setContent(entity.getContentRu());
                region.setName(regionEntity.getNameRu());
                category.setName(categoryEntity.getNameRu());
            }
            default -> {
                dto.setTitle(entity.getTitleEn());
                dto.setDescription(entity.getDescriptionEn());
                dto.setContent(entity.getContentEn());
                region.setName(regionEntity.getNameEn());
                category.setName(categoryEntity.getNameEn());
            }
        }
        dto.setRegion(region);
        dto.setCategory(category);
        dto.setTagName(tagNameDTOList);
        dto.setLikeCount(articleLikeService.getLikeCount(id));
        return dto;
    }

    public List<ArticleShortInfoDTO> mostReadArticles(Integer limit, Language language) {
        List<ArticleEntity> entities = articleRepository.mostReadArticles(limit);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entities) {
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    //11
    public List<ArticleShortInfoDTO> getLastArticleByTagName(Long tagId, Integer limit, Language language) {
        List<ArticleTagNameEntity> list = articleTagNameRepository.findAllByTagNameIdOrderByCreatedDateDesc(tagId);

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleTagNameEntity articleAndTagNameEntity : list) {
            ArticleEntity entity = get(articleAndTagNameEntity.getArticleId(),language);
            if (entity != null) {
                dtoList.add(toDo(entity,language));
            }
            if (dtoList.size() >= limit) {
                break;
            }
        }
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getArticleByNewsTypeIdAndRegionId(Integer typeId, Integer limit, Integer regionId, Language language) {
        List<ArticleNewsTypeEntity> articles = getArticles(typeId, limit,language);
        List<ArticleEntity> list = new LinkedList<>();
        for (ArticleNewsTypeEntity articleId : articles) {
            list.add(articleRepository.find(articleId.getArticleId()));
        }
        List<ArticleEntity> byRegionId = articleRepository.findByRegionId(regionId);
        List<ArticleEntity> entityList = new LinkedList<>();
        for (ArticleEntity articleEntity : list) {
            for (ArticleEntity entity : byRegionId) {
                if (Objects.equals(articleEntity.getId(), entity.getId())) {
                    entityList.add(articleEntity);
                }
            }
        }

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    public PageImpl<ArticleShortInfoDTO> getArticleByRegionIdPagination(Integer regionId, Pageable pageable, Language language) {
        Page<ArticleEntity> byRegionIdByPageable = articleRepository.findByRegionIdByPageable(regionId, pageable);
        return getArticleShortInfoDTOS(pageable, byRegionIdByPageable,language);

    }

    public List<ArticleShortInfoDTO> getLastArticleCategoryId(Integer categoryId, Integer limit, Language language) {
        List<ArticleEntity> byCategoryId = articleRepository.findByCategoryId(categoryId, limit);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : byCategoryId) {
            dtoList.add(toDo(entity,language));
        }
        return dtoList;
    }

    public PageImpl<ArticleShortInfoDTO> getLastArticleCategoryIdAndByPagination(Integer categoryId, Pageable pageable, Language language) {
        Page<ArticleEntity> byRegionIdByPageable = articleRepository.findByCategoryIdByPageable(categoryId, pageable);
        return getArticleShortInfoDTOS(pageable, byRegionIdByPageable,language);
    }

    public Integer increaseArticleViewCount(String id, Language language) {
        ArticleEntity entity = getArticle(id,language);
        entity.setViewCount(entity.getViewCount() + 1);
        articleRepository.save(entity);
        return entity.getViewCount();
    }

    public Integer increaseArticleShareCount(String id, Language language) {
        ArticleEntity entity = getArticle(id,language);
        entity.setSharedCount(entity.getSharedCount() + 1);
        articleRepository.save(entity);
        return entity.getSharedCount();
    }

    public PageImpl<ArticleShortInfoDTO> filter(FilterArticleDTO filter, Pageable pageable, Integer publisherId, Language language) {
        PaginationResultDTO<ArticleEntity> articleFilter = customRepository.articleFilter(filter, pageable, publisherId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : articleFilter.getList()) {
            dtoList.add(toDo(entity,language));
        }
        return new PageImpl<>(dtoList, pageable, articleFilter.getTotalSize());
    }

    @NotNull
    private PageImpl<ArticleShortInfoDTO> getArticleShortInfoDTOS(Pageable pageable, Page<ArticleEntity> byRegionIdByPageable,Language language) {
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        List<ArticleEntity> content = byRegionIdByPageable.getContent();
        for (ArticleEntity entity : content) {
            dtoList.add(toDo(entity,language));
        }
        return new PageImpl<>(dtoList, pageable, byRegionIdByPageable.getTotalElements());
    }

    private ArticleShortInfoDTO toDo(ArticleEntity entity,Language language) {
        AttachEntity attachEntity = attachService.get(entity.getImageId(),language);
        AttachDTO attachDTO = attachService.toDTO(attachEntity);

        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitleUz(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());
        dto.setDescriptionUz(entity.getDescriptionUz());
        dto.setDescriptionRu(entity.getDescriptionRu());
        dto.setDescriptionEn(entity.getDescriptionEn());
        dto.setContentUz(entity.getContentUz());
        dto.setContentRu(entity.getContentRu());
        dto.setContentEn(entity.getContentEn());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setImage(attachDTO);
        return dto;
    }

    public ArticleDTO toDoForComment(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitleUz(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());
        return dto;
    }


    public ArticleEntity get(String id,Language language) {
//        return articleRepository.findArticleById(id).orElseThrow(() -> new AppBadException("Article not found"));
        return articleRepository.findById(id).orElseThrow(() -> {
            log.warn("Article not found{}", id);
            return new AppBadException(resourceBundleService.getMessage("article.not.found",language));
        });
    }

    public ArticleEntity getArticle(String id,Language language) {
        return articleRepository.getArticle(id).orElseThrow(() -> {
            log.warn("Article not found{}", id);
            return new AppBadException(resourceBundleService.getMessage("article.not.found",language));
        });
    }

    private List<ArticleNewsTypeEntity> getArticles(Integer id, Integer limit,Language language) {
        /*List<String> listArticleId = articleNwsTypeRepository.findByNewsTypeIdOrderByCreatedDateDesc(id, limit);*/
        List<ArticleNewsTypeEntity> articleIds = articleNwsTypeRepository.findByNewsTypeIdOrderByCreatedDateDesc(id, limit);
        if (articleIds.isEmpty()) {
            log.warn("New Type not found{}", id);
            throw new AppBadException(resourceBundleService.getMessage("new.type.not.found",language));
        }
        return articleIds;
    }

    public ArticleDTO getForComment(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitleUz(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());
        return dto;
    }
    public ArticleDTO getForSavedArticle(String id, Language language) {
        ArticleEntity entity = getArticle(id, language);
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitleUz(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());
        dto.setDescriptionUz(entity.getDescriptionUz());
        dto.setDescriptionRu(entity.getDescriptionRu());
        dto.setDescriptionEn(entity.getDescriptionEn());
        dto.setContentUz(entity.getContentUz());
        dto.setContentRu(entity.getContentRu());
        dto.setContentEn(entity.getContentEn());
        dto.setAttach(attachService.toDTO(attachService.get(entity.getImageId(), language)));
        return dto;
    }



}
