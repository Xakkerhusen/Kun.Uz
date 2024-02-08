package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.category.Category;
import com.example.kun_Uz_Lesson_1.dto.category.CreateCategoryDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.CategoryService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Category API list",description = "API list for Category")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    @Operation( summary = "Api for create", description = "this api is used to create category ")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryDTO dto,
                                    HttpServletRequest request) {
        log.info("Create category{}", dto);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/adm/{id}")
    @Operation( summary = "Api for update", description = "this api is used to update category ")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Category dto,
                                    HttpServletRequest request) {
        log.info("Update category{}", dto);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    @Operation( summary = "Api for delete", description = "this api is used to delete category ")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        log.info("Delete category{}", id);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @CrossOrigin(value = "*")
    @GetMapping("/adm/pagination")
    @Operation( summary = "Api for getAll", description = "this api is used to get all category ")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "2") Integer size,
                                    HttpServletRequest request) {
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "orderNumber");
        log.info("Get category by pagination{}", pageable);
        return ResponseEntity.ok(categoryService.all(pageable));
    }

    @GetMapping("/lang")
    @Operation( summary = "Api for getAllByLang", description = "this api is used to get all category by language ")
    public ResponseEntity<List<Category>> getAllByLang(@RequestParam(value = "lang", defaultValue = "uz") Language language) {
        log.info("Get category by language{}", language);
        return ResponseEntity.ok(categoryService.gatAllByLang(language));
    }


}
