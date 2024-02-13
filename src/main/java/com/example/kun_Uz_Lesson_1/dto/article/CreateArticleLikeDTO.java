package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateArticleLikeDTO {
    private LikeStatus status;
}
