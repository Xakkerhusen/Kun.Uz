package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Region {
    private Integer id;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
    private Boolean visible;
    private Long orderNumber;
    private Language language;

    private String nameUz;
    private String nameRu;
    private String nameEn;
}
