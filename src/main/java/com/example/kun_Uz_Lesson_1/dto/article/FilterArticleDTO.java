package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class FilterArticleDTO {
    /*(id,title,region_id,category_id,crated_date_from,created_date_to
        published_date_from,published_date_to,moderator_id,publisher_id,status) with Pagination (PUBLISHER)
        ArticleShortInfo*/
    private String id;
    private String titleUz;
    private String titleRu;
    private String titleEn;

    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private LocalDate createdFrom;
    private LocalDate createdTo;
    private LocalDate publishedFrom;
    private LocalDate publishedTo;
    private ArticleStatus status;

}
