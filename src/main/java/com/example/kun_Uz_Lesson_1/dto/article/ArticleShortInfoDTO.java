package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {
    private String id;

    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String title;

    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String description;

    private String contentUz;
    private String contentRu;
    private String contentEn;
    private String content;
    private Integer regionId;
    private LocalDateTime publishedDate;
    private AttachDTO image;
}
