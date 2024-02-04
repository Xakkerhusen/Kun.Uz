package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity,Integer>, PagingAndSortingRepository<ArticleEntity,Integer> {

}
