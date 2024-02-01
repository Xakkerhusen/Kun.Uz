package com.example.kun_Uz_Lesson_1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_sent_history")
@Setter
@Getter
public class EmailSentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "message",columnDefinition = "text")
    private String message;
    @Column(name = "email")
    private String email;




}
