package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.entity.CategoryEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private UUID id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private String imagesId;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    private ArticleStatus status;

    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publishedId;

}
