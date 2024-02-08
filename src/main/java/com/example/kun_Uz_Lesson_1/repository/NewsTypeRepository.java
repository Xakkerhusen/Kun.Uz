package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.NewsTypeEntity;
import com.example.kun_Uz_Lesson_1.mapper.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsTypeRepository extends CrudRepository<NewsTypeEntity, Integer>, PagingAndSortingRepository<NewsTypeEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update NewsTypeEntity set nameUz=:nameUz,nameRu=:nameRu,nameEn=:nameEn,orderNumber=:orderNumber where id=:id")
    Integer updateArticle(Integer id, String nameUz, String nameRu, String nameEn, Long orderNumber);

    @Transactional
    @Modifying
    @Query("update NewsTypeEntity ate set ate.visible=false where ate.id=:id")
    Integer deleteArticleTypeById(Integer id);

    @Query(value = "select ate.id,ate.order_number,ate.created_date,ate.name_uz from article_type ate ", nativeQuery = true)
    List<Mapper> allByLangUz2();

    @Query("from NewsTypeEntity ate where ate.visible=true")
    Page<NewsTypeEntity> findAllArticle(Pageable pageable);

    @Query("from NewsTypeEntity where visible=true ")
    List<NewsTypeEntity> findAllArticleType();

}
