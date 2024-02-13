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
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Tag(name = "News type API list",description = "API list for News type")
@RestController
@RequestMapping("/articleType")
public class NewsTypeController {
    @Autowired
    private NewsTypeService articleTypeService;

    @PostMapping("/adm")
    @Operation( summary = "Api for create", description = "this api is used to create news type ")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedNewsTypeDTO articleType,
                                    HttpServletRequest request) {
         log.info("Create news type{} ",articleType);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.create(articleType));
    }


    @PutMapping("/adm/{id}")
    @Operation( summary = "Api for update", description = "this api is used to update news type ")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody NewsType dto,
                                    HttpServletRequest request) {
        log.info("Update news type by id {}",dto);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    @Operation( summary = "Api for delete", description = "this api is used to delete news type ")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        log.info("Delete nws type by id{}",id);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/adm/pagination")
    @Operation( summary = "Api for all", description = "this api is used to get all news type ")
    public ResponseEntity<?> all(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "2") Integer size,
                                 HttpServletRequest request) {
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get all nws type by pagination{}",pageable);
        return ResponseEntity.ok(articleTypeService.all(pageable));
    }

    @GetMapping("/lang")
    @Operation( summary = "Api for getAllByLang", description = "this api is used to get all news type by language ")
    public ResponseEntity<List<NewsType>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        log.info("Get all nws type by language{}",language);
        return ResponseEntity.ok(articleTypeService.allByLang(language));
    }


}
