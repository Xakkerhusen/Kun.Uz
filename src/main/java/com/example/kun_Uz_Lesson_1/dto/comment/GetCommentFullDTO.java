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
public class GetCommentFullDTO {
    /*(id,created_date,update_date,profile(id,name,surname),content,article(id,title),reply_id,)*/
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Profile profile;
    private ArticleDTO article;
}
