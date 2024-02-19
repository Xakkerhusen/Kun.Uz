package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.CreateSavedArticleDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.service.SavedArticleService;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Saved Article API list", description = "API list for Saved Article")
@RestController
@RequestMapping("/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/any")
    @Operation(summary = "API for create", description = "this api is used to create save article")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    public ResponseEntity<?>create(@RequestBody CreateSavedArticleDTO dto,
                                   @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language){

        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.create(dto, profileId,language));
    }
    @DeleteMapping("/any/{id}")
    @Operation(summary = "API for delete", description = "this api is used to delete save article")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    public ResponseEntity<?>delete(@PathVariable("id")String articleId,
                                   @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language){

        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.delete( articleId,profileId,language));
    }

    @GetMapping("/any")
    @Operation(summary = "API for get all", description = "this api is used to get all save article")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    public ResponseEntity<?>getAll(@RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.getAll(language,profileId));
    }




}
