package com.example.kun_Uz_Lesson_1.dto.article;

import com.example.kun_Uz_Lesson_1.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsType {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;
    private Long orderNumber;

    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;


}
