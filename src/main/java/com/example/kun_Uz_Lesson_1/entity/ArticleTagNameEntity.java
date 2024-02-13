package com.example.kun_Uz_Lesson_1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_tag_name")
public class ArticleTagNameEntity extends BaseEntity {

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "tag_name_id")
    private Long tagNameId;
    @ManyToOne
    @JoinColumn(name = "tag_name_id",insertable = false,updatable = false)
    private TagNameEntity tagName;
}
