package com.example.kun_Uz_Lesson_1.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCommentDTO {
    @NotBlank(message = " Comment content required ")
    private String content;
    private Integer profileId;
    private Integer articleId;
    private Integer replyId;

}
