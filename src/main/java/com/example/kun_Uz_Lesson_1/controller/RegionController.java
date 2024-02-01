package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.region.CreatedRegionDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.RegionService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
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
                                    HttpServletRequest request) {

       HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Region region,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id,region));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }

    @CrossOrigin(value = "*")
    @GetMapping("/adm")
    public ResponseEntity<?> all(HttpServletRequest request) {

        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.all());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<Region>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(regionService.getAllByLang(language));
    }
}
