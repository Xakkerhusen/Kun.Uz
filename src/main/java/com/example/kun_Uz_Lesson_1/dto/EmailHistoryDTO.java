package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private String sentSms;
    private LocalDateTime sentSmsTime;
    private String email;

    private Integer profileId;
}
