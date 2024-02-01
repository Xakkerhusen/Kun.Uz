package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.EmailSentHistoryEntity;
import com.example.kun_Uz_Lesson_1.entity.SmsHistoryEntity;
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
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer>, PagingAndSortingRepository<SmsHistoryEntity,Integer> {
    List<SmsHistoryEntity> findByPhone(String phone);

    @Query("from SmsHistoryEntity where createdDate between ?1 and ?2")
    List<SmsHistoryEntity> findAllSmsHistoryByDate(LocalDateTime from, LocalDateTime to);

    @Query("from SmsHistoryEntity ")
    Page<SmsHistoryEntity> findAllHistory(Pageable pageable);
}
