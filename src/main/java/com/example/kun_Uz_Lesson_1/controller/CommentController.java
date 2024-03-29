package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.comment.CreateCommentDTO;
import com.example.kun_Uz_Lesson_1.dto.comment.FilterCommentDTO;
import com.example.kun_Uz_Lesson_1.dto.comment.UpdateCommentDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.CommentService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Comment API list", description = "API for comment")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
     //1
    @Operation(summary = "API for create", description = "this api is used to create comment")
    @PostMapping("/any/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR','PUBLISHER')")
    public ResponseEntity<?> create(@PathVariable("id") String articleId, @RequestBody(required = false) CreateCommentDTO dto,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.create(articleId, dto, profileId,language));
    }

    //2
    @Operation(summary = "API for update", description = "this api is used to update comment")
    @PutMapping("/any/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR','PUBLISHER')")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody(required = false) UpdateCommentDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.update(id, dto, profileId));
    }

    //3
    @Operation(summary = "API for delete", description = "this api is used to delete comment")
    @DeleteMapping("/any/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR','PUBLISHER')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer commnetId,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.delete(commnetId,profileId,language));
    }

    //4
    @Operation(summary = "API for getCommentListByArticleId", description = "this api is used to get all Comment List By Article Id ")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentListByArticleId(@PathVariable("id") String articleId,
                                                       @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language) {
        return ResponseEntity.ok(commentService.getCommentListByArticleId(articleId,language));
    }

    //5
    @Operation(summary = "API for getCommentListByPagination", description = "this api is used to get all  Comment List By Pagination")
    @GetMapping("")
    public ResponseEntity<?> getCommentListByPagination(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                        @RequestParam(value = "size",defaultValue = "2")Integer size,
                                                        @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(commentService.getCommentListByPagination(pageable,language));
    }

    //6
    @Operation(summary = "API for filter", description = "this api is used to get all  Comment List By Filter")
    @PostMapping("/adm/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> filter(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "size",defaultValue = "2")Integer size,
                                    @RequestBody FilterCommentDTO dto) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(commentService.filter(dto,pageable));
    }

    //7
    @Operation(summary = "API for getRepliedCommentListByCommentId", description = "this api is used to Get Replied Comment List by Comment Id")
    @GetMapping("/getReplied/{id}")
    public ResponseEntity<?> getRepliedCommentListByCommentId(@PathVariable("id")Integer id,
                                                              @RequestHeader(value = "Accept-Language",defaultValue = "UZ")Language language) {
        return ResponseEntity.ok(commentService.getRepliedCommentListByCommentId(id,language));
    }


}
