package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.TagNameDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.TagNameService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Tag(name = "Tag Name API list",description = "API list for Tag Name")
@RestController
@RequestMapping("/tagName")
public class TagNameController {

    @Autowired
    private TagNameService tagNameService;

    @PostMapping("/adm")
    @Operation(summary = "Api for create",description = "this api is used to create tag name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagNameDTO> create(@RequestBody TagNameDTO dto) {
        return ResponseEntity.ok(tagNameService.create(dto));
    }
}
