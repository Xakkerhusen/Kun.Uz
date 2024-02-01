package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.EmailHistoryDTO;
import com.example.kun_Uz_Lesson_1.dto.SmsHistoryDTO;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
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
import java.util.List;
import java.util.PrimitiveIterator;

@RestController
@RequestMapping("/smsHistory")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    @GetMapping("/{phone}")
    public ResponseEntity<List  <SmsHistoryDTO>>getHistoryByPhoneNumber(@PathVariable("phone")String phone){
        return ResponseEntity.ok(smsHistoryService.getSmsHistoryByPhoneNumber(phone));
    }

    @GetMapping("/date")
    public ResponseEntity<List<SmsHistoryDTO>> getByDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(smsHistoryService.getByDate(date));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<SmsHistoryDTO>>getAllHistoryByPagination(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                                  @RequestParam(value = "size",defaultValue = "2")Integer size,
                                                                  HttpServletRequest request){
        HTTPRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        return ResponseEntity.ok(smsHistoryService.getAllHistoryByPagination(pageable));
    }
}
