package com.example.kun_Uz_Lesson_1.dto.region;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedRegionDTO {
    @NotNull(message = "Order number required")
    private Long orderNumber;

    @NotBlank(message = "Name Uz required")
    @Size(min = 2,max = 20,message = "Name Uz must be between 2 and 10 character")
    private String nameUz;
    @NotBlank(message = "Name Ru required")
    private String nameRu;
    @NotBlank(message = "Name En required")
    private String nameEn;
    private String name;
}
