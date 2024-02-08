package com.example.kun_Uz_Lesson_1.dto.profile;

import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.intellij.lang.annotations.RegExp;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedProfileDTO {
    @NotBlank(message = "Profile name required")
    private String name;
    @NotBlank(message = "Profile surname required")
    private String surname;
    @NotBlank(message = "Profile email required")
    @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "Profile password required")
    private String password;

    private ProfileStatus status;
    @NotNull(message = "Profile role required")
    private ProfileRole role;
}
