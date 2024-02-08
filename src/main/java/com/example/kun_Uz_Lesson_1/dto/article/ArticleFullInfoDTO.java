package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.dto.category.Category;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {
    /*id(uuid),title,description,content,shared_count,
        region(key,name),category(key,name),published_date,view_count,like_count,
        tagList(name)*/
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

    private Integer shareCount;
    private Integer viewCount;
    private LocalDateTime publishedDate;
    private Integer likeCount;

    private String region;
    private String category;


}
