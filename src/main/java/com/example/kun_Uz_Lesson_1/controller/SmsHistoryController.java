package com.example.kun_Uz_Lesson_1.controller;


import com.example.kun_Uz_Lesson_1.dto.SmsHistoryDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.service.SmsHistoryService;
import com.example.kun_Uz_Lesson_1.utils.HTTPRequestUtil;
import com.example.kun_Uz_Lesson_1.utils.SpringSecurityUtil;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name = "SMS History API list", description = "API list for SMS History")
@RestController
@RequestMapping("/smsHistory")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    @PostMapping("")
    @Operation(summary = "Api for getHistoryByPhoneNumber", description = "this api is used to get sms history by phone number ")
    public ResponseEntity<List<SmsHistoryDTO>> getHistoryByPhoneNumber(@RequestBody SmsHistoryDTO dto) {
        log.info("Get sms History By Phone Number{}", dto.getPhone());
        return ResponseEntity.ok(smsHistoryService.getSmsHistoryByPhoneNumber(dto.getPhone()));
    }

    @GetMapping("/date")
    @Operation(summary = "Api for getByDate", description = "this api is used to get sms history by date ")
    public ResponseEntity<List<SmsHistoryDTO>> getByDate(@RequestParam("date") LocalDate date) {
        log.info("Get sms History By Date{}", date);
        return ResponseEntity.ok(smsHistoryService.getByDate(date));
    }

    @GetMapping("/adm/pagination")
    @Operation(summary = "Api for getAllHistoryByPagination", description = "this api is used to get all sms history by pagination ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageImpl<SmsHistoryDTO>> getAllHistoryByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                             @RequestParam(value = "size", defaultValue = "2") Integer size) {
        SpringSecurityUtil.getCurrentUser().getId();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        log.info("Get all sms History By pagination{}", pageable);
        return ResponseEntity.ok(smsHistoryService.getAllHistoryByPagination(pageable));
    }
}
