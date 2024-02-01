package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_history")
@Setter
@Getter
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "sent_sms")
    private String sentSms;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status = SmsStatus.NEW;

}
