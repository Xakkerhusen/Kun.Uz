package com.example.kun_Uz_Lesson_1.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCommentDTO {
    private String content;
    private String articleId;

}
