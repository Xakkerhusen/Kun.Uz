package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.EmailSentHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailSentHistoryRepository extends CrudRepository<EmailSentHistoryEntity, Integer>, PagingAndSortingRepository<EmailSentHistoryEntity,Integer> {

    List<EmailSentHistoryEntity> findByEmail(String email);

    @Query("from EmailSentHistoryEntity  where createdDate between ?1 and ?2")
    List<EmailSentHistoryEntity> findByCreatedDate(LocalDateTime from, LocalDateTime to);

    @Query("from EmailSentHistoryEntity ")
    Page<EmailSentHistoryEntity> findAllHistory(Pageable pageable);


    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query("SELECT count (s) from EmailSentHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);

}
