package com.example.kun_Uz_Lesson_1.mapper;

import java.time.LocalDateTime;

public interface Mapper {
    Integer getId();
    Long getOrderNumber();
    String getNameUz();
    String getNameEn();
    String getNameRu();
    LocalDateTime getCreatedDate();
}
