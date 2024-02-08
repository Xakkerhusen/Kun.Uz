package com.example.kun_Uz_Lesson_1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "article_news_type")
public class ArticleNewsTypeEntity extends BaseEntity {
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "news_type_id")
    private Integer newsTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_type_id",insertable = false,updatable = false)
    private NewsTypeEntity newsType;

}
