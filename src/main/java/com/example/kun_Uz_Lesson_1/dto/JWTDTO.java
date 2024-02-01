package com.example.kun_Uz_Lesson_1.dto;

import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JWTDTO {
    private Integer id;
    private ProfileRole role;

    public JWTDTO(Integer id) {
        this.id = id;
    }
}
