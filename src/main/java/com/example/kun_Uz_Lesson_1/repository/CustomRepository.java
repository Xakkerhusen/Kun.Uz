package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.dto.article.FilterArticleDTO;
import com.example.kun_Uz_Lesson_1.dto.comment.CommentDTO;
import com.example.kun_Uz_Lesson_1.dto.comment.FilterCommentDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.entity.ArticleEntity;
import com.example.kun_Uz_Lesson_1.entity.CommentEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
    @Autowired
    private EntityManager entityManager;


    public PaginationResultDTO<ProfileEntity> profileFilter(FilterProfileDTO filter, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append(" and lower(name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getSurname() != null) {
            builder.append(" and lower(surname)like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getRole() != null) {
            builder.append(" and role=:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            builder.append(" and createdDate <= :toDate");
            params.put("toDate", filter.getToDate());
        }
        StringBuilder selectBuilder = new StringBuilder(" from ProfileEntity p where 1=1 and visible=true ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder(" select count(p) from ProfileEntity p where 1=1 and visible=true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<ProfileEntity> enitityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new PaginationResultDTO<>(totalElement, enitityList);
    }


    public PaginationResultDTO<ArticleEntity> articleFilter(FilterArticleDTO filter, Pageable pageable, Integer publisherId) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getTitleUz() != null) {
            builder.append(" and lower(titleUz) like :titleUz ");
            params.put("titleUz", "%"+filter.getTitleUz().toLowerCase()+"%");
        }
        if (filter.getTitleRu() != null) {
            builder.append(" and lower(titleRu) like :titleRu ");
            params.put("titleRu", "%"+filter.getTitleRu().toLowerCase()+"%");
        }
        if (filter.getTitleEn() != null) {
            builder.append(" and lower(titleEn) like :titleEn ");
            params.put("titleEn", "%"+filter.getTitleEn().toLowerCase()+"%");
        }
        if (filter.getModeratorId() != null) {
            builder.append(" and moderatorId=:moderatorId ");
            params.put("moderatorId", filter.getModeratorId());
        }
        if (filter.getCategoryId() != null) {
            builder.append(" and categoryId=:categoryId ");
            params.put("categoryId", filter.getCategoryId());
        }
        if (filter.getRegionId() != null) {
            builder.append(" and regionId=:regionId ");
            params.put("regionId", filter.getRegionId());
        }
        if (publisherId != null) {
            builder.append(" and publisherId=:publisherId ");
            params.put("publisherId", publisherId);
        }
        if (filter.getStatus() != null) {
            builder.append(" and status=:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getCreatedFrom() != null && filter.getCreatedTo() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedTo(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCreatedFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedFrom(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCreatedTo() != null) {
            builder.append(" and createdDate <= :toDate ");
            params.put("toDate", filter.getCreatedTo());
        }
        if (filter.getPublishedFrom() != null && filter.getPublishedTo() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getPublishedFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getPublishedTo(), LocalTime.MAX);
            builder.append(" and publishedDate  between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getPublishedFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getPublishedFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getPublishedFrom(), LocalTime.MAX);
            builder.append(" and publishedDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getPublishedTo() != null) {
            builder.append(" and publishedDate <= :toDate ");
            params.put("toDate", filter.getPublishedTo());
        }
        StringBuilder selectBuilder = new StringBuilder(" from ArticleEntity a where 1=1 and visible=true ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder(" select count(a) from ArticleEntity a where 1=1 and visible=true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<ArticleEntity> enitityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new PaginationResultDTO<>(totalElement, enitityList);
    /* from ArticleEntity a where 1=1  and lower(titleUz) like :titleUz  and regionId=:regionId  order by createdDate desc */
    }

    public PaginationResultDTO<CommentEntity> filterComment(FilterCommentDTO dto, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        Map<String,Object>params=new HashMap<>();
        if (dto.getId()!=null){
            builder.append(" and id=:id ");
            params.put("id",dto.getId());
        }
        if (dto.getContent()!=null){
            builder.append(" and content=:content ");
            params.put("content",dto.getContent());
        }
        if (dto.getReplyId()!=null){
            builder.append(" and replyId=:replyId ");
            params.put("replyId",dto.getReplyId());
        }
        if (dto.getProfileId()!=null){
            builder.append(" and profileId=:profileId ");
            params.put("profileId",dto.getProfileId());
        }
        if (dto.getArticleId()!=null){
            builder.append(" and articleId=:articleId ");
            params.put("articleId",dto.getArticleId());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() != null) {
            LocalDateTime fromDate = LocalDateTime.of(dto.getCreatedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(dto.getCreatedDateTo(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (dto.getCreatedDateFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(dto.getCreatedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(dto.getCreatedDateFrom(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (dto.getCreatedDateTo() != null) {
            builder.append(" and createdDate <= :toDate ");
            params.put("toDate", dto.getCreatedDateTo());
        }
        if (dto.getUpdateDateFrom() != null && dto.getUpdateDateTo() != null) {
            LocalDateTime fromDate = LocalDateTime.of(dto.getUpdateDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(dto.getUpdateDateTo(), LocalTime.MAX);
            builder.append(" and updateDate  between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (dto.getUpdateDateFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(dto.getUpdateDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(dto.getUpdateDateFrom(), LocalTime.MAX);
            builder.append(" and updateDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (dto.getUpdateDateTo() != null) {
            builder.append(" and updateDate <= :toDate ");
            params.put("toDate", dto.getUpdateDateTo());
        }

        StringBuilder selectBuilder = new StringBuilder(" from CommentEntity c where 1=1 and visible=true ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");
        StringBuilder countBuilder = new StringBuilder(" select count(a) from CommentEntity a where 1=1 and visible=true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }
        List<CommentEntity> enitityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new PaginationResultDTO<>(totalElement, enitityList);

    }
}
