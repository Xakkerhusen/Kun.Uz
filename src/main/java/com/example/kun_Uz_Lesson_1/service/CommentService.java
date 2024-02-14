package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.dto.comment.*;
import com.example.kun_Uz_Lesson_1.entity.ArticleEntity;
import com.example.kun_Uz_Lesson_1.entity.CommentEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.CommentRepository;
import com.example.kun_Uz_Lesson_1.repository.CustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CustomRepository customRepository;

    public CreateCommentDTO create(String id, CreateCommentDTO dto, Integer profileId) {
        CommentEntity entity = new CommentEntity();
        articleService.getArticle(id);
        entity.setContent(dto.getContent());
        entity.setArticleId(id);
        entity.setProfileId(profileId);
        if (dto.getReplyId() != null) {
            get(dto.getReplyId());
            entity.setReplyId(dto.getReplyId());
        }
        commentRepository.save(entity);
        return dto;
    }

    public CommentEntity get(Integer replyId) {
        return commentRepository.findById(replyId).orElseThrow(() -> {
            log.warn("Comment not found{}", replyId);
            return new AppBadException("Comment not found");
        });
    }

    public UpdateCommentDTO update(Integer id, UpdateCommentDTO dto, Integer profileId) {
        CommentEntity entity = get(id);
        if (!entity.getProfileId().equals(profileId)) {
            log.warn("This profile has not left such a comment");
            throw new AppBadException("This profile has not left such a comment");
        }
        entity.setContent(dto.getContent());
        if (dto.getArticleId() != null) {
            entity.setArticleId(dto.getArticleId());
        }
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        return dto;
    }

    public boolean delete(Integer id, Integer profileId) {
        ProfileEntity profileEntity = profileService.get(profileId);
        get(id);
        Integer effectiveRows = 0;
        if (profileEntity.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            effectiveRows = commentRepository.deleteCommentById(id);
        } else {
            Optional<CommentEntity> top1ByProfileId = commentRepository.findTop1ByProfileId(profileId);
            if (top1ByProfileId.isEmpty()) {
                log.warn("No comments were found for this profile");
                throw new AppBadException("No comments were found for this profile");
            }
            if (profileEntity.getId().equals(profileId) &&
                    profileEntity.getId().equals(top1ByProfileId.get().getProfileId())) {
                effectiveRows = commentRepository.deleteCommentById(id);
            }
        }
        if (effectiveRows == 0) {
            log.warn("Comment not deleted{}", id);
            throw new AppBadException("Comment not deleted");
        }
        return true;
    }

    public List<GetCommentDTO> getCommentListByArticleId(String id) {
        List<CommentEntity> allByArticleId = commentRepository.getAllByArticleId(id);
        List<GetCommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : allByArticleId) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public PageImpl<CommentDTO> getCommentListByPagination(Pageable pageable) {
        Page<CommentEntity> all = commentRepository.findAll(pageable);
        List<CommentEntity> content = all.getContent();
        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : content) {
            dtoList.add(toDo3(entity));
        }
        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }


    public PageImpl<CommentDTO> filter(FilterCommentDTO dto, Pageable pageable) {
        PaginationResultDTO<CommentEntity> resultFilter = customRepository.filterComment(dto, pageable);
        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : resultFilter.getList()) {
            if (entity.getVisible().equals(true)) {
                dtoList.add(toDo2(entity));
            }
        }
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    public List<GetCommentDTO> getRepliedCommentListByCommentId(Integer id) {
        List<CommentEntity> allByReplyId = commentRepository.findAllByReplyId(id);
        List<GetCommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : allByReplyId) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }


    private GetCommentDTO toDo(CommentEntity entity) {
        GetCommentDTO dto = new GetCommentDTO();
        ProfileEntity profileEntity = profileService.get(entity.getProfileId());
        dto.setId(entity.getId());
        dto.setProfile(profileService.toDoForComment(profileEntity));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdatedDate());
        return dto;
    }

    private CommentDTO toDo2(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setArticleId(entity.getArticleId());
        dto.setProfileId(entity.getProfileId());
        dto.setReplyId(entity.getReplyId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    private CommentDTO toDo3(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        ProfileEntity profileEntity = profileService.get(entity.getProfileId());
        String articleId = entity.getArticleId();
        ArticleEntity articleEntity = articleService.get(articleId);
        dto.setId(entity.getId());
        dto.setProfile(profileService.toDoForComment(profileEntity));
        dto.setArticle(articleService.getForComment(articleEntity));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdatedDate());
        return dto;
    }


}
