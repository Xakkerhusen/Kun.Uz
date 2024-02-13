package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "commrnt_like")
public class CommentLikeEntity extends BaseEntity{
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne()
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne()
    @JoinColumn(name = "comment_id",insertable = false,updatable = false)
    private CommentEntity comment;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}
