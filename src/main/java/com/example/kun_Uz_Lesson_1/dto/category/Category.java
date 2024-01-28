package com.example.kun_Uz_Lesson_1.dto.category;

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
public class Category {
    protected Integer id;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
    private Boolean visible;
    private Long orderNumber;

    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
}
