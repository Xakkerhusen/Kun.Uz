package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.AttachService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,
                ProfileRole.USER, ProfileRole.PUBLISHER);

        AttachDTO dto = attachService.save(file);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
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
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?>getByPagination(@RequestParam(name = "page",defaultValue = "1")Integer page,
                                            @RequestParam(name = "size",defaultValue = "2")Integer size,
                                            HttpServletRequest request){
        HTTPRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(attachService.getByPagination(pageable));
    }

    @DeleteMapping("/adm/{uuid}")
    public ResponseEntity<?>delete(@PathVariable("uuid")String uuid, HttpServletRequest request){
        HTTPRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(uuid));
    }


    private static final String EXTERNAL_FILE_PATH = "D:/Asosiy modul(JAVA)/kun.uz/kun_Uz_Lesson_1/uploads/";

    @GetMapping("/download/{fileName}")
    public void downloadPDFResource( HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) throws IOException {

        File file = new File(EXTERNAL_FILE_PATH + fileName);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

      /*      *
     * In a regular HTTP response, the Content-Disposition response header is a
     * header indicating if the content is expected to be displayed inline in the
     * browser, that is, as a Web page or as part of a Web page, or as an
     * attachment, that is downloaded and saved locally.
     *


     *
     * Here we have mentioned it to show inline*/

            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }


    }
}
