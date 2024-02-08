package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.EmailHistoryDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.EmailHistoryService;
import com.example.kun_Uz_Lesson_1.service.MailSenderService;
import com.example.kun_Uz_Lesson_1.service.SmsHistoryService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Tag(name = "Email History API list",description = "API list for Email History")
@RestController
@RequestMapping("/emailHistory")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/{email}")
    @Operation( summary = "Api for getByEmail", description = "this api is used to get email history by email ")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@PathVariable("email") String email) {
        log.info("Get all email history {}",email);
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/date")
    @Operation( summary = "Api for getByDate", description = "this api is used to get email history by date ")
    public ResponseEntity<List<EmailHistoryDTO>> getByDate(@RequestParam("date") LocalDate date) {
        log.info("Get email history by date {}",date);
        return ResponseEntity.ok(emailHistoryService.getByDate(date));
    }

    @GetMapping("/adm/pagination")
    @Operation( summary = "Api for getByPagination", description = "this api is used to get all email history by pagination ")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                     HttpServletRequest request) {
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get email history by pagination {}",pageable);
        return ResponseEntity.ok(emailHistoryService.getByPagination(pageable));

    }

}
