package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleShortInfoDTO;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.dto.article.FilterArticleDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ArticleService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Article API list", description = "API list for Article")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/mod")
    @Operation(summary = "API for create", description = "this api is used to create article")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedArticleDTO dto) {
        log.info("Create article{}", dto.getTitleUz());
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleService.create(dto, profileId));
    }

    @PutMapping("/mod/{uuid}")
    @Operation(summary = "API for update", description = "this api is used to update article")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> update(@Valid @RequestBody CreatedArticleDTO dto, @PathVariable(name = "uuid") String id) {
        log.info("Update article By Id {}", dto);
        Integer moderatorId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleService.update(dto, id, moderatorId));
    }

    @DeleteMapping("/mod/{uuid}")
    @Operation(summary = "API for delete", description = "this api is used to delete article")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String id) {
        log.info("Delete article By Id {}", id);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/pub/{uuid}")
    @Operation(summary = "API for changeStatusById", description = "this api is used to change article status")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<?> changeStatusById(@PathVariable("uuid") String id) {
        log.info("Change article status By Id {}", id);
        Integer publisherId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleService.changeStatusById(id, publisherId));
    }

    //5,6
    @GetMapping("/get/{id}")
    @Operation(summary = "API for getLastFiveArticleByTypes", description = "This api is used to get the last five articles of the given type")
    public ResponseEntity<?> getLastFiveArticleByTypes(@PathVariable("id") Integer id,
                                                       @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        log.info("Get Last Five Article By Types {}", id);
        return ResponseEntity.ok(articleService.getLastFiveArticleByTypes(id, limit));
    }

    //7,9
    @GetMapping("/articleId")
    @Operation(summary = "API for listOfArticlesExceptGivenIds", description = "This api is used to get the list of articles except given IDs")
    public ResponseEntity<?> listOfArticlesExceptGivenIds(@RequestParam("ids") String[] id, @RequestParam(value = "limit", defaultValue = "4") Integer limit) {

        log.info("List Of Articles Except Given Ids {}", (Object) id);
        return ResponseEntity.ok(articleService.listOfArticlesExceptGivenIds(id, limit));
    }

    //8
    @GetMapping("/language")
    @Operation(summary = "API for getArticleByIdAndLanguage", description = "This api is used to get article by language")
    public ResponseEntity<?> getArticleByIdAndLanguage(@RequestParam("id") String id, @RequestParam("language") Language language) {
        log.info("Get Article By Id And Language{}", id);
        return ResponseEntity.ok(articleService.getArticleByIdAndLanguage(id, language));
    }

    //10
    @GetMapping("/mostReaArticle")
    @Operation(summary = "API for mostReadArticles", description = "This api is used to get most read article ")
    public ResponseEntity<?> mostReadArticles(@RequestParam(value = "limit", defaultValue = "1000") Integer limit) {
        log.info("Most Read Articles");
        return ResponseEntity.ok(articleService.mostReadArticles(limit));
    }

    //11
    @GetMapping("/getLastArticleByTagName/{id}")
    @Operation(summary = "API for getLastArticleByTagName", description = "This api is used to get last article by tag name ")
    public ResponseEntity<?> getLastArticleByTagName(@PathVariable("id")Long id,
                                                     @RequestParam(value = "limit", defaultValue = "4") Integer limit) {
        log.info("Get Last Article By TagName{}", id);
        return ResponseEntity.ok(articleService.getLastArticleByTagName(id,limit));
    }


    //12
    @GetMapping("/getArticleByNewsTypeIdAndRegionId/{id}")
    @Operation(summary = "API for getArticleByNewsTypeIdAndRegionId", description = "This api is used to get article by news type id and region id")
    public ResponseEntity<?> getArticleByNewsTypeIdAndRegionId(@PathVariable("id") Integer typeId,
                                                               @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                                               @RequestParam("regionId") Integer regionId) {
        log.info("Get Last Five Article By Types {}", typeId);
        return ResponseEntity.ok(articleService.getArticleByNewsTypeIdAndRegionId(typeId, limit, regionId));
    }

    //13
    @GetMapping("/getArticleByRegionIdPagination/{id}")
    @Operation(summary = "API for getArticleByNewsTypeIdAndRegionIdPagination", description = "This api is used to get all article by region id")
    public ResponseEntity<?> getArticleByRegionIdPagination(
            @PathVariable("id") Integer regionId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get Last Five Article By Types {}", regionId);
        return ResponseEntity.ok(articleService.getArticleByRegionIdPagination( regionId, pageable));
    }

    //14
    @GetMapping("/getLastArticleCategoryId/{id}")
    @Operation(summary = "API for getLastArticleCategoryId", description = "This api is used to get article by category id and by limit")
    public ResponseEntity<?> getLastArticleCategoryId(@PathVariable("id") Integer categoryId,@RequestParam(value = "limit",defaultValue = "5")Integer limit) {
        log.info("Get Last Five Article By Types {}", categoryId);
        return ResponseEntity.ok(articleService.getLastArticleCategoryId( categoryId,limit));
    }

    //15
    @GetMapping("/getLastArticleCategoryIdAndByPagination/{id}")
    @Operation(summary = "API for getLastArticleCategoryIdAndByPagination", description = "This api is used to get category by region id and by pagination")
    public ResponseEntity<?> getLastArticleCategoryIdAndByPagination(@PathVariable("id") Integer categoryId,
                                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get Last Five Article By Types {}", categoryId);
        return ResponseEntity.ok(articleService.getLastArticleCategoryIdAndByPagination( categoryId,pageable));
    }

    //16
    @PutMapping("/viewCount/{id}")
    @Operation(summary = "API for increaseArticleViewCount", description = "This api works to change view count of article")
    public ResponseEntity<?> increaseArticleViewCount(@PathVariable("id") String id) {
        log.info("Increase Article View Count {}", id);
        return ResponseEntity.ok(articleService.increaseArticleViewCount(id));
    }

    //17
    @PutMapping("/shareCount/{id}")
    @Operation(summary = "API for increaseArticleViewCount", description = "This api works to change Share count of article")
    public ResponseEntity<?> increaseArticleShareCount(@PathVariable("id") String id) {
        log.info("Increase Article Share Count {}", id);
        return ResponseEntity.ok(articleService.increaseArticleShareCount(id));
    }

    //18
    @PostMapping("/pub/filter")
    @Operation( summary = "Api for filter", description = "this api is used to filter article ")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<PageImpl<ArticleShortInfoDTO>>filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                               @RequestBody FilterArticleDTO filter) {
        Integer publisherId = SpringSecurityUtil.getCurrentUser().getId();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get profile by filter{}", pageable);
        return ResponseEntity.ok(articleService.filter(filter, pageable,publisherId));
    }

}
