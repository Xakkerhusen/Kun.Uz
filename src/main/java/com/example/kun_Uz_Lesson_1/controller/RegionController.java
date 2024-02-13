package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.controller.config.CustomUserDetails;
import com.example.kun_Uz_Lesson_1.dto.region.CreatedRegionDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.RegionService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Tag(name = "Region API list",description = "API list for Region")
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    @Operation( summary = "Api for create", description = "this api is used to create region ")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedRegionDTO dto,
                                    HttpServletRequest request) {
       log.info("Create region {}",dto);
//       HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();

        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/adm/{id}")
    @Operation( summary = "Api for update", description = "this api is used to update region ")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Region region,
                                    HttpServletRequest request) {
        log.info("Update region by id{}",region.getNameUz());
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.update(id,region));
    }

    @DeleteMapping("/adm/{id}")
    @Operation( summary = "Api for delete", description = "this api is used to delete region ")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        log.info("Delete region by id {}",id);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }

    @CrossOrigin(value = "*")
    @GetMapping("/adm")
    @Operation( summary = "Api for all", description = "this api is used to get all region ")
    public ResponseEntity<?> all(HttpServletRequest request) {
        log.info("Get all region {}");
        HTTPRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.all());
    }

    @GetMapping("/lang")
    @Operation( summary = "Api for getAllByLang", description = "this api is used to get all region by language")
    public ResponseEntity<List<Region>> getAllByLang(@RequestParam(value = "language", defaultValue = "uz") Language language) {
        log.info("Get  region by language {}",language);
        return ResponseEntity.ok(regionService.getAllByLang(language));
    }


    
}
