package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.JWTDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.CreatedProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.ProfileService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CreatedProfileDTO dto,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@RequestBody(required = false) Profile dto,
                                    @PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(dto, id));
    }

    @PutMapping("/adm/updateDetail")
    public ResponseEntity<?> updateDetail(@RequestBody Profile dto,
                                          HttpServletRequest request) {

        Integer id = HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN, ProfileRole.USER,
                ProfileRole.MODERATOR, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(profileService.updateDetail(dto, id));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<Profile>>    filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                    @RequestBody FilterProfileDTO filter) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(profileService.filter(filter, pageable));
    }


}
