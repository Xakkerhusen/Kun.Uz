package com.example.kun_Uz_Lesson_1.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedNewsTypeDTO {
    @NotNull(message = "NewsType order number required")
    private Long orderNumber;
    @NotNull(message = "NewsType name uz required")
    private String nameUz;
    @NotNull(message = "NewsType name ru required")
    private String nameRu;
    @NotNull(message = "NewsType name en required")
    private String nameEn;
    private String name;
}
