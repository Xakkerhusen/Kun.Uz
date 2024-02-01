package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.SmsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private String sentSms;
    private String phone;
    private SmsStatus status;
}
