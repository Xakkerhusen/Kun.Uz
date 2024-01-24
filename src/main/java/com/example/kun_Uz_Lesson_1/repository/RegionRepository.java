package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update RegionEntity set nameUz=:nameUz,nameEn=:nameEn,nameRu=:nameRu where id=:id")
    Integer update(Integer id, String nameUz, String nameEn, String nameRu);

    @Transactional
    @Modifying
    @Query("delete RegionEntity where id=:id")
    Integer deleteRegionById(Integer id);

    @Query("from RegionEntity ")
    List<RegionEntity> getAllByLanUz();

    @Query("from RegionEntity ")
    List<RegionEntity> getAllByLanEn();
    @Query("from RegionEntity ")
    List<RegionEntity> getAllByLanRu();
}
