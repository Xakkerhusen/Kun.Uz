package com.example.kun_Uz_Lesson_1.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationProfileDTO {
   private String phoneNumber;
   private String name;
   private String surname;
   private String email;
   private String password;
}
