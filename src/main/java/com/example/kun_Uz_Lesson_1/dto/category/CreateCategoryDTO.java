package com.example.kun_Uz_Lesson_1.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class CreateCategoryDTO {
    @NotNull(message = "Category order number required")
    private Long orderNumber;
    @NotNull(message = "NewsType name uz required")
    private String nameUz;
    @NotNull(message = "NewsType name ru required")
    private String nameRu;
    @NotNull(message = "NewsType name en required")
    private String nameEn;
}
