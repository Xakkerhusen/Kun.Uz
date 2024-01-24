package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "region")

public class RegionEntity extends BaseEntity {
    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name ="name_uz" )
    private String nameUz;
    @Column(name = "name_ru" )
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;

}
