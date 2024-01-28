package com.example.kun_Uz_Lesson_1.dto.profile;

import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedProfileDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
}
