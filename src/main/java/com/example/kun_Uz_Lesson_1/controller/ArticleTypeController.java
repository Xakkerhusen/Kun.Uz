package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleType;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.service.ArticleTypeService;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatedArticleDTO articleType,
                                    @RequestHeader(value = "Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(articleTypeService.create(articleType)):
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ArticleType dto,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(articleTypeService.update(id, dto)):
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(articleTypeService.delete(id)):
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pagination")
    public ResponseEntity<?> all(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "2") Integer size,
                                 @RequestHeader("Authorization") String jwt) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(articleTypeService.all(pageable)):
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleType>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(articleTypeService.allByLang(language));
    }


}
