package com.example.kun_Uz_Lesson_1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsSendDTO {
    private String code;
    private String phoneNumber;
}
