package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.comment.CreatedCommentLikeDTO;
import com.example.kun_Uz_Lesson_1.entity.CommentLikeEntity;
import com.example.kun_Uz_Lesson_1.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentService commentService;

    public Object create(Integer commentId, Integer profileId, CreatedCommentLikeDTO dto) {
        commentService.get(commentId);
        Optional<CommentLikeEntity> optional = commentLikeRepository.findTop1ByCommentId(commentId);

        CommentLikeEntity commentEntity = new CommentLikeEntity();

        if (optional.isEmpty()) {
            commentEntity.setCommentId(commentId);
            commentEntity.setProfileId(profileId);
            commentEntity.setStatus(dto.getStatus());
            commentLikeRepository.save(commentEntity);
            return "SUCCESS save";
        }
        CommentLikeEntity entity = optional.get();
        if (entity.getStatus().equals(dto.getStatus()) &&
                entity.getCommentId().equals(commentId) && entity.getProfileId().equals(profileId)) {
            commentLikeRepository.deleteById(entity.getId());
            return "SUCCESS delete";
        } else if (entity.getCommentId().equals(commentId) && !entity.getProfileId().equals(profileId)) {
            commentEntity.setCommentId(commentId);
            commentEntity.setProfileId(profileId);
            commentEntity.setStatus(dto.getStatus());
            commentLikeRepository.save(commentEntity);
            return "SUCCESS save";
        } else {
            return commentLikeRepository.update(entity.getId(), dto.getStatus(), LocalDateTime.now()) != 0 ? "update" : "articleId not found";
        }

    }
}
