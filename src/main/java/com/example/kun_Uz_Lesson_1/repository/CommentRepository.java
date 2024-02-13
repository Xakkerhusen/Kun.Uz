package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity,Integer>, PagingAndSortingRepository<CommentEntity,Integer> {
    @Query("from CommentEntity where visible=true and id=?1")
    Optional<CommentEntity> findById(Integer id);
    Optional<CommentEntity>findTop1ByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible=false where id=?1")
    Integer deleteCommentById(Integer id);

    @Query("from CommentEntity where visible=true and articleId=?1")
    List<CommentEntity>getAllByArticleId(String id);
    @Query("from CommentEntity where visible=true ")
    Page<CommentEntity>findAll(Pageable pageable);
    @Query("from CommentEntity where visible=true and replyId=?1")
    List<CommentEntity>findAllByReplyId(Integer id);
}
