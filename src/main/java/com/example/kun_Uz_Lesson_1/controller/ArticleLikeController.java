package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.article.CreateArticleLikeDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ArticleLikeService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Tag(name = "Article Like API list", description = "API list for Article Like")
@RestController
@RequestMapping("/articleLike")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @Operation(summary = "API for create", description = "this api is used to create article like")
    @PostMapping("/any/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR','PUBLISHER')")
    public ResponseEntity<?> create(@PathVariable("id")String articleId,
                                    @RequestBody CreateArticleLikeDTO dto){
        log.info("Create article like ");
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleLikeService.create(articleId, profileId,dto));
    }
}
