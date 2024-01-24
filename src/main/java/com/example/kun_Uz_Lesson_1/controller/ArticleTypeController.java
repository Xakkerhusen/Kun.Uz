package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.ArticleType;
import com.example.kun_Uz_Lesson_1.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleType> create(@RequestBody ArticleType articleType) {
        return ResponseEntity.ok(articleTypeService.create(articleType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleType> update(@PathVariable("id") Integer id,
                                          @RequestBody ArticleType dto) {
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ArticleType>> all(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(articleTypeService.all(pageable));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleType>>getAllByLang(@RequestParam("language")String language){
        return ResponseEntity.ok(articleTypeService.allByLang(language));
    }

}
