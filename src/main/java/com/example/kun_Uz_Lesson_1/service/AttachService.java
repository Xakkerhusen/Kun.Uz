package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.AttachDTO;
import com.example.kun_Uz_Lesson_1.entity.AttachEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private ResourceBundleService resourceBundleService;

//    public String saveToSystem(MultipartFile file) { // mazgi.png
//        try {
//            File folder = new File("attaches");
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get("attaches/" + file.getOriginalFilename()); // attaches/mazgi.png
//            Files.write(path, bytes);
//            return file.getOriginalFilename();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //    public byte[] loadImage(String fileName) { // zari.jpg
//        BufferedImage originalImage;
//        try {
//            originalImage = ImageIO.read(new File("attaches/" + fileName));
//            // attaches/zari.jpg
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//            baos.flush();
//            byte[] imageInByte;
//            imageInByte = baos.toByteArray();
//            baos.close();
//            return imageInByte;
//        } catch (Exception e) {
//            return new byte[0];
//        }
//    }
    public byte[] loadImage(String attachId, Language language) { // dasdasd-dasdasda-asdasda-asdasd.jpg
        return getBytes(attachId,language);
    }

    public byte[] open_general(String attachId, Language language) {
        return getBytes(attachId, language);
    }

    public String getYmDString() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        int day = LocalDate.now().getDayOfMonth();
        return year + "/" + month + "/" + day; // 2024/4/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachDTO save(MultipartFile file) { // mazgi.png
        try {
            String pathFolder = getYmDString(); // 2022/04/23
            File folder = new File("uploads/" + pathFolder);
            if (!folder.exists()) { // uploads/2022/04/23
                folder.mkdirs();
            }
            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); // mp3/jpg/npg/mp4

            byte[] bytes = file.getBytes();
            Path path = Paths.get("uploads/" + pathFolder + "/" + key + "." + extension);
            //                         uploads/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            //                         uploads/ + Path + id + extension
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setSize(file.getSize());
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setId(key);
            entity.setPath(pathFolder);

            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Save {}",e.getMessage());
        }
        return null;
    }

    private byte[] getBytes(String attachId, Language language) {
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity entity = get(id,language);
        byte[] data;
        try {
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + attachId);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
        }
        return new byte[0];
    }

    public AttachEntity get(String id, Language language) {
        return attachRepository.findById(id).orElseThrow(() -> new AppBadException(resourceBundleService.getMessage("file.not.found",language)));
    }


    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setUrl(serverUrl + "/attach/open_general/" + entity.getId() + "." + entity.getExtension());
        return dto;
    }


    public PageImpl<AttachDTO> getByPagination(Pageable pageable) {
        Page<AttachEntity> all = attachRepository.findAll(pageable);
        List<AttachEntity> content = all.getContent();
        List<AttachDTO> dtoList = new LinkedList<>();
        for (AttachEntity entity : content) {
            dtoList.add(toDoDetail(entity));
        }
        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    private AttachDTO toDoDetail(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setExtension(entity.getExtension());
        dto.setOriginalName(entity.getOriginalName());
        dto.setCreatedData(entity.getCreatedDate());
        return dto;
    }

    public boolean delete(String uuid, Language language) {
        AttachEntity attachEntity = get(uuid,language);
        new File(String.valueOf(Path.of("uploads/" + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getExtension()))).deleteOnExit();
        Integer effectiveRows = attachRepository.deleteAttach(uuid);
        return effectiveRows != 0;
    }

    public ResponseEntity download(String attachId, Language language) {
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity entity = get(id,language);
        try {
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
               return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
//                return resource;
            } else {
                log.error("Could not read the file!{}",resource);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
                log.error("Error: {}",e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}
