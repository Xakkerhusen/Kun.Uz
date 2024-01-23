package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;


}
