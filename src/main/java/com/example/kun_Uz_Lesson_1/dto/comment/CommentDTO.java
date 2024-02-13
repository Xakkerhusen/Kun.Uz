package com.example.kun_Uz_Lesson_1.dto.comment;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private boolean visible;
    private Integer replyId;
    private Integer profileId;
    private String articleId;
    private Profile profile;
    private ArticleDTO article;

}
