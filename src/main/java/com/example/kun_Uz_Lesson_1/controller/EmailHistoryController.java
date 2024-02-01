package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.EmailHistoryDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.EmailHistoryService;
import com.example.kun_Uz_Lesson_1.service.MailSenderService;
import com.example.kun_Uz_Lesson_1.service.SmsHistoryService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
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

@RestController
@RequestMapping("/emailHistory")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/{email}")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/date")
    public ResponseEntity<List<EmailHistoryDTO>> getByDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(emailHistoryService.getByDate(date));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                     HttpServletRequest request) {
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(emailHistoryService.getByPagination(pageable));

    }

}
