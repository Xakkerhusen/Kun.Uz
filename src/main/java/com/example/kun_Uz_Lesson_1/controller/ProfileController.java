package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.profile.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.CreatedProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ProfileService;
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
@Tag(name = "Profile API list",description = "API list for Profile")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    @Operation( summary = "Api for create", description = "this api is used to create profile ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedProfileDTO dto) {
        log.info("Create profile {}",dto.getEmail());
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/adm/{id}")
    @Operation( summary = "Api for update", description = "this api is used to update profile ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody(required = false) Profile dto,
                                    @PathVariable("id") Integer id) {
        log.info("Update profile by id {}",dto.getEmail());
        return ResponseEntity.ok(profileService.update(dto, id));
    }

    @PutMapping("/any/updateDetail")
    @Operation( summary = "Api for updateDetail", description = "this api is used to Update profile (only his own) ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR','PUBLISHER')")
    public ResponseEntity<?> updateDetail(@RequestBody Profile dto) {
        log.info("Update profile by id (only his own) {}",dto.getEmail());
        Integer id = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(profileService.updateDetail(dto, id));
    }

    @DeleteMapping("/adm/{id}")
    @Operation( summary = "Api for delete", description = "this api is used to delete profile ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        log.info("Delete profile by id (only his own) {}",id);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PostMapping("/filter")
    @Operation( summary = "Api for filter", description = "this api is used to filter profile ")
    public ResponseEntity<PageImpl<Profile>>filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                    @RequestBody FilterProfileDTO filter) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get profile by filter{}",pageable);
        return ResponseEntity.ok(profileService.filter(filter, pageable));
    }


}
