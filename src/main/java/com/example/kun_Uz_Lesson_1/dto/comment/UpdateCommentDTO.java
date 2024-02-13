package com.example.kun_Uz_Lesson_1.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCommentDTO {
    private String content;
    private String articleId;

}
