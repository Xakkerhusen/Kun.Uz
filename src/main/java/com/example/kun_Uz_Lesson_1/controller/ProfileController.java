package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.CreatedProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.service.ProfileService;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
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

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatedProfileDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(profileService.create(dto)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody(required = false) Profile dto,
                                    @PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(profileService.update(dto, id)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/updateDetail")
    public ResponseEntity<?> updateDetail(@RequestBody Profile dto,
                                          @RequestHeader("Authorization") String jwt) {

        Integer id = JWTUtil.decode(jwt).getId();
        return ResponseEntity.ok(profileService.updateDetail(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt) ? ResponseEntity.ok(profileService.delete(id)) :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<Profile>> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                    @RequestBody FilterProfileDTO filter) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(profileService.filter(filter, pageable));
    }


}
