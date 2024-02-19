package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveArticleFullInfoDTO {
    private Integer id;
    private ArticleDTO article;
}
