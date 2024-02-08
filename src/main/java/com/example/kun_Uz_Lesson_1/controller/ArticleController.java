package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.article.ArticleDTO;
import com.example.kun_Uz_Lesson_1.dto.article.CreatedArticleDTO;
import com.example.kun_Uz_Lesson_1.enums.ArticleStatus;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ArticleService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Tag(name = "Article API list", description = "API list for Article")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/mod")
    @Operation(summary = "API for create", description = "this api is used to create article")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedArticleDTO dto,
                                    HttpServletRequest request) {
        log.info("Create {}", dto.getTitleUz());
        Integer profileId = HTTPRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, profileId));
    }

    @PutMapping("/mod/{uuid}")
    @Operation(summary = "API for update", description = "this api is used to update article")
    public ResponseEntity<?> update(@Valid @RequestBody CreatedArticleDTO dto, @PathVariable(name = "uuid") String id,
                                    HttpServletRequest request) {
        log.info("Update By Id {}", dto);
        Integer moderatorId = HTTPRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, id, moderatorId));
    }

    @DeleteMapping("/mod/{uuid}")
    @Operation(summary = "API for delete", description = "this api is used to delete article")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String id,
                                    HttpServletRequest request) {
        log.info("Delete By Id {}", id);
        HTTPRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/pub/{uuid}")
    @Operation(summary = "API for changeStatusById", description = "this api is used to change article status")
    public ResponseEntity<?> changeStatusById(@PathVariable("uuid") String id,
                                              HttpServletRequest request) {
        log.info("Change status By Id {}", id);
        Integer publisherId = HTTPRequestUtil.getProfileId(request, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatusById(id, publisherId));
    }

    //5,6
    @GetMapping("/{id}")
    @Operation(summary = "API for getLastFiveArticleByTypes", description = "This api is used to get the last five articles of the given type")
    public ResponseEntity<?> getLastFiveArticleByTypes(@PathVariable("id") Integer id,
                                                       @RequestParam(value = "limit",defaultValue = "5") Integer limit) {
        log.info("Get Last Five Article By Types {}", id);
        return ResponseEntity.ok(articleService.getLastFiveArticleByTypes(id, limit));
    }

    //7,9
    @GetMapping("/articleId")
    @Operation(summary = "API for listOfArticlesExceptGivenIds", description = "This api is used to get the list of articles except given IDs")
    public ResponseEntity<?>listOfArticlesExceptGivenIds(@RequestParam("ids")String []id,@RequestParam(value = "limit",defaultValue = "4")Integer limit){
        return ResponseEntity.ok(articleService.listOfArticlesExceptGivenIds(id,limit));
    }

    //8
    @GetMapping("/language")
    @Operation(summary = "API for getArticleByIdAndLanguage", description = "This api is used to get article by language")
    public ResponseEntity<?>getArticleByIdAndLanguage(@RequestParam("id")String id, @RequestParam("language")Language language){
        return ResponseEntity.ok(articleService.getArticleByIdAndLanguage(id, language));
    }
    //10
    @GetMapping("/mostReaArticle")
    @Operation(summary = "API for mostReadArticles", description = "This api is used to get most read article ")
    public ResponseEntity<?>mostReadArticles(@RequestParam(value = "limit",defaultValue = "1000")Integer limit){
        return ResponseEntity.ok(articleService.mostReadArticles(limit));
    }

//12
@GetMapping("/getArticleByNewsTypeIdAndRegionId/{id}")
@Operation(summary = "API for getArticleByNewsTypeIdAndRegionId", description = "This api is used to get article by news type id and region id")
public ResponseEntity<?> getArticleByNewsTypeIdAndRegionId(@PathVariable("id") Integer typeId,
                                                   @RequestParam(value = "limit",defaultValue = "5") Integer limit,
                                                   @RequestParam("regionId")Integer regionId) {
    log.info("Get Last Five Article By Types {}", typeId);
    return ResponseEntity.ok(articleService.getArticleByNewsTypeIdAndRegionId(typeId, limit,regionId));
}











    //16
    @PutMapping("/viewCount/{id}")
    @Operation(summary = "API for increaseArticleViewCount", description = "This api works to change view count of article")
    public ResponseEntity<?>increaseArticleViewCount(@PathVariable("id")String id){
        return ResponseEntity.ok(articleService.increaseArticleViewCount(id));
    }

    //17
    @PutMapping("/shareCount/{id}")
    @Operation(summary = "API for increaseArticleViewCount", description = "This api works to change Share count of article")
    public ResponseEntity<?>increaseArticleShareCount(@PathVariable("id")String id){
        return ResponseEntity.ok(articleService.increaseArticleShareCount(id));
    }

}
