package com.example.kun_Uz_Lesson_1.entity;

import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Setter
@Getter
public class ArticleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "title_uz")
    private String titleUz;
    @Column(name = "title_ru")
    private String titleRu;
    @Column(name = "title_en")
    private String titleEn;
    @Column(name = "description_uz", columnDefinition = "text")
    private String descriptionUz;
    @Column(name = "description_ru", columnDefinition = "text")
    private String descriptionRu;
    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;
    @Column(name = "content_uz", columnDefinition = "text")
    private String contentUz;
    @Column(name = "content_ru", columnDefinition = "text")
    private String contentRu;
    @Column(name = "content_en", columnDefinition = "text")
    private String contentEn;
    @Column(name = "shared_count")
    private Integer sharedCount=0;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "visible")
    private Boolean visible = true;
    @Column(name = "view_count")
    private Integer viewCount=0;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;


    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

}
