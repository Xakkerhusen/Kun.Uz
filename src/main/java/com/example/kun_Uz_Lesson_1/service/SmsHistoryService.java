package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.EmailHistoryDTO;
import com.example.kun_Uz_Lesson_1.dto.SmsHistoryDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.RegistrationProfileDTO;
import com.example.kun_Uz_Lesson_1.entity.SmsHistoryEntity;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.SmsHistoryRepository;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public List<SmsHistoryDTO> getSmsHistoryByPhoneNumber(String phone) {
        List<SmsHistoryEntity> byPhone = smsHistoryRepository.findByPhone(phone);
        if (byPhone.isEmpty()) {
            throw new AppBadException("History not found");
        }
        List<SmsHistoryDTO>dtoList=new LinkedList<>();
        for (SmsHistoryEntity entity : byPhone) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public List<SmsHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<SmsHistoryEntity> smsHistoryByDate = smsHistoryRepository.findAllSmsHistoryByDate(from, to);
        List<SmsHistoryDTO>dtoList=new LinkedList<>();
        for (SmsHistoryEntity entity : smsHistoryByDate) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public PageImpl<SmsHistoryDTO> getAllHistoryByPagination(Pageable pageable) {
        Page<SmsHistoryEntity> allHistory = smsHistoryRepository.findAllHistory(pageable);
        List<SmsHistoryEntity> content = allHistory.getContent();
        long totalElements = allHistory.getTotalElements();
        List<SmsHistoryDTO>dtoList=new LinkedList<>();
        for (SmsHistoryEntity entity : content) {
            dtoList.add(toDo(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    private SmsHistoryDTO toDo(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setSentSms(entity.getSentSms());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void create(RegistrationProfileDTO dto, String password) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setPhone(dto.getPhoneNumber());
        smsHistoryEntity.setSentSms(String.valueOf(password));
        smsHistoryRepository.save(smsHistoryEntity);
    }

}
