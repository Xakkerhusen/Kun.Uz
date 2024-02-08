package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.AttachService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "Attach API list",description = "API list for Attach")
@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    //    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
//        String fileName = attachService.saveToSystem(file);
//        return ResponseEntity.ok().body(fileName);
//    }

    @PostMapping("/adm/upload")
    @Operation(summary = "API for upload",description = "this api is used to save files (jpg,png,mp3,mp4.....)")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {
        log.info("Upload {}",file);
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,
                ProfileRole.USER, ProfileRole.PUBLISHER);

        AttachDTO dto = attachService.save(file);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "API for open",description = "this api is used to open images")
    public byte[] open(@PathVariable("fileName") String fileName) {
        log.info("Open {}",fileName);
        if (fileName != null && !fileName.isEmpty()) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    @Operation(summary = "API for open_general",description = "this api is used to open all files")
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        log.info("Open general {}",fileName);
        return attachService.open_general(fileName);
    }

    @GetMapping("/adm/pagination")
    @Operation(summary = "API for getByPagination",description = "this api is used to get all files ")
    public ResponseEntity<?>getByPagination(@RequestParam(name = "page",defaultValue = "1")Integer page,
                                            @RequestParam(name = "size",defaultValue = "2")Integer size,
                                            HttpServletRequest request){
        HTTPRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get attach  {}",pageable);
        return ResponseEntity.ok(attachService.getByPagination(pageable));
    }

    @DeleteMapping("/adm/{uuid}")
    @Operation(summary = "API for delete",description = "this api is used to delete  files ")
    public ResponseEntity<?>delete(@PathVariable("uuid")String uuid, HttpServletRequest request){
        log.info("Delete attach  {}",uuid);
        HTTPRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(uuid));
    }
    @GetMapping("/download/{fineName}")
    @Operation(summary = "API for download",description = "this api is used to download  files ")
    public ResponseEntity<?> download(@PathVariable("fineName") String fileName) {
        log.info("Download attach  {}",fileName);
        return attachService.download(fileName);
//        Resource file = attachService.download(fileName);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }





}
