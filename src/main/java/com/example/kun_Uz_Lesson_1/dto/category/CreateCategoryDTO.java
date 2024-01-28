package com.example.kun_Uz_Lesson_1.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCategoryDTO {
    private Long orderNumber;

    private String nameUz;
    private String nameRu;
    private String nameEn;
}
