package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.comment.CreatedCommentLikeDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.CommentLikeService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Comment Like API list", description = "API list for Comment Like")
@RestController
@RequestMapping("/commentLike")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @Operation(summary = "API for create", description = "this api is used to create comment like")
    @PostMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable("id")Integer commentId,
                                    @RequestBody CreatedCommentLikeDTO dto,
                                    HttpServletRequest request){
        log.info("Create comment like ");
        Integer profileId = HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_MODERATOR,
                ProfileRole.ROLE_PUBLISHER, ProfileRole.Role_USER);
        return ResponseEntity.ok(commentLikeService.create(commentId, profileId,dto));
    }

}
