package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
public class FilterProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private ProfileRole role;
    private LocalDate fromDate;
    private LocalDate toDate;
}
