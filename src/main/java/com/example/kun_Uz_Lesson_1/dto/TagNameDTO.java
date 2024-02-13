package com.example.kun_Uz_Lesson_1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagNameDTO {
    private Long id;
    private String name;
    private LocalDateTime createdDate;

}
