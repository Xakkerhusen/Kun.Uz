package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity extends BaseEntity{
    /*id,profile_id,article_id,created_date,status,*/
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne()
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne()
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
