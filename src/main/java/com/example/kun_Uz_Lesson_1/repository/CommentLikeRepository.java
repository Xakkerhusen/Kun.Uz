package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.CommentLikeEntity;
import com.example.kun_Uz_Lesson_1.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {
    @Query("from CommentLikeEntity where  commentId=?1 and profileId=?2")
    Optional<CommentLikeEntity> findTop1ByCommentId(Integer commentId, Integer profileId);


    @Transactional
    @Modifying
    @Query("update CommentLikeEntity set status=?2 , updatedDate=?3 where id=?1")
    Integer update(Integer id, LikeStatus status, LocalDateTime now);
}
