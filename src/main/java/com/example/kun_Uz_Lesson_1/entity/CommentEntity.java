package com.example.kun_Uz_Lesson_1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(name = "content", columnDefinition = "text")
    private String content;
    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
}
