package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.Region;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
     private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<Region>create(@RequestBody Region dto){
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean>update(@PathVariable("id")Integer id,
                                         @RequestBody Region region){
        return ResponseEntity.ok(regionService.update(id,region));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(regionService.delete(id));
    }
    @CrossOrigin(value = "*")
    @GetMapping("")
    public ResponseEntity<List<Region>>all(){
        return ResponseEntity.ok(regionService.all());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<Region>>getAllByLang(@RequestParam("language")String language){
        return ResponseEntity.ok(regionService.getAllByLang(language));
    }
}
