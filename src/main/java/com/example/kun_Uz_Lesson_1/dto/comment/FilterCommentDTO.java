package com.example.kun_Uz_Lesson_1.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterCommentDTO {
    private Integer id;
    private String content;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate updateDateFrom;
    private LocalDate updateDateTo;
    private boolean visible;
    private Integer replyId;
    private Integer profileId;
    private String articleId;

}
