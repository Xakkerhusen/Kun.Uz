package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.category.Category;
import com.example.kun_Uz_Lesson_1.dto.category.CreateCategoryDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateCategoryDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(categoryService.create(dto)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Category dto,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(categoryService.update(id, dto)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(categoryService.delete(id)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @CrossOrigin(value = "*")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "2") Integer size,
                                    @RequestHeader("Authorization") String jwt) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "orderNumber");
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(categoryService.all(pageable)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/lang")
    public ResponseEntity<List<Category>> getAllByLang(@RequestParam(value = "lang", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(categoryService.gatAllByLang(language));
    }


}
