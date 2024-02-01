package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleType;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ArticleTypeService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CreatedArticleDTO articleType,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(articleType));
    }


    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ArticleType dto,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> all(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "2") Integer size,
                                 HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(articleTypeService.all(pageable));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleType>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(articleTypeService.allByLang(language));
    }


}
