package com.example.kun_Uz_Lesson_1.controller;


import com.example.kun_Uz_Lesson_1.dto.article.CreatedNewsTypeDTO;
import com.example.kun_Uz_Lesson_1.dto.article.NewsType;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.NewsTypeService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Tag(name = "News type API list",description = "API list for News type")
@RestController
@RequestMapping("/newsType")
public class NewsTypeController {
    @Autowired
    private NewsTypeService articleTypeService;

    @PostMapping("/adm")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation( summary = "Api for create", description = "this api is used to create news type ")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedNewsTypeDTO articleType) {
         log.info("Create news type{} ",articleType);
        return ResponseEntity.ok(articleTypeService.create(articleType));
    }


    @PutMapping("/adm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation( summary = "Api for update", description = "this api is used to update news type ")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody NewsType dto,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("Update news type by id {}",dto);
        return ResponseEntity.ok(articleTypeService.update(id, dto,language));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation( summary = "Api for delete", description = "this api is used to delete news type ")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("Delete nws type by id{}",id);
        return ResponseEntity.ok(articleTypeService.delete(id,language));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation( summary = "Api for all", description = "this api is used to get all news type ")
    public ResponseEntity<?> all(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get all nws type by pagination{}",pageable);
        return ResponseEntity.ok(articleTypeService.all(pageable));
    }

    @GetMapping("/lang")
    @Operation( summary = "Api for getAllByLang", description = "this api is used to get all news type by language ")
    public ResponseEntity<List<NewsType>> getAllByLang(@RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("Get all nws type by language{}",language);
        return ResponseEntity.ok(articleTypeService.allByLang(language));
    }


}
