package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.region.CreatedRegionDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.RegionService;
import com.example.kun_Uz_Lesson_1.controller.config.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CreatedRegionDTO dto,
                                    @RequestHeader("Authorization") String jwt) {

        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(regionService.create(dto)):
        ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Region region,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(regionService.update(id, region)):
        ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(regionService.delete(id)):
        ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @CrossOrigin(value = "*")
    @GetMapping("")
    public ResponseEntity<?> all(@RequestHeader("Authorization") String jwt) {
        return JWTUtil.checkingRole(jwt)? ResponseEntity.ok(regionService.all()):
        ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/lang")
    public ResponseEntity<List<Region>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(regionService.getAllByLang(language));
    }
}
