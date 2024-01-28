package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.CategoryEntity;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
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
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>, PagingAndSortingRepository<CategoryEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity c set c.nameUz=:nameUz,c.nameRu=:nameRu,c.nameEn=:nameEn where c.id=:id")
    Integer update(Integer id, String nameUz, String nameRu, String nameEn);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=:id")
    Integer deleteCategoryById(Integer id);

    @Query("from CategoryEntity where visible=true ")
    Page<CategoryEntity> findAllCategory(Pageable pageable);

    @Query("from CategoryEntity where visible=true ")
    List<CategoryEntity> findAllCategoryByLang();

}
