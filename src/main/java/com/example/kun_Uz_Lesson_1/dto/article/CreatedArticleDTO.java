package com.example.kun_Uz_Lesson_1.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedArticleDTO {
    /*title,description,content,image_id, region_id,category_id, articleType(array))*/

    @NotBlank(message = "title uz required")
    private String titleUz;
    @NotBlank(message = "title ru required")
    private String titleRu;
    @NotBlank(message = "title en required")
    private String titleEn;

    @NotBlank(message = "description uz required")
    private String descriptionUz;
    @NotBlank(message = "description en required")
    private String descriptionEn;
    @NotBlank(message = "description ru required")
    private String descriptionRu;

    @NotBlank(message = "content uz required")
    private String contentUz;
    @NotBlank(message = "content ru required")
    private String contentRu;
    @NotBlank(message = "content en required")
    private String contentEn;

    private String imageId;
    private Integer regionId;
    private Integer categoryId;
    private List<Integer> newsType;
    private List<Long> tagName;
}
