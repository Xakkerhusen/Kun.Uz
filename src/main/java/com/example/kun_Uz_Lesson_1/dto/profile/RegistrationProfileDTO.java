package com.example.kun_Uz_Lesson_1.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.intellij.lang.annotations.RegExp;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationProfileDTO {
   private String phoneNumber;
   @NotBlank(message = "name required")
   private String name;
   @NotBlank(message = "surname required")
   private String surname;
   @NotBlank(message = "email required")
   @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
   private String email;
   @NotBlank(message = "password required")
   private String password;
}
