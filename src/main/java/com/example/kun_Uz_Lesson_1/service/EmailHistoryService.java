package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.EmailHistoryDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.RegistrationProfileDTO;
import com.example.kun_Uz_Lesson_1.entity.EmailSentHistoryEntity;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.EmailSentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailSentHistoryRepository emailSentHistoryRepository;


    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailSentHistoryEntity> optional = emailSentHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("History not found");
        }
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSentHistoryEntity entity : optional) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    public List<EmailHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<EmailSentHistoryEntity> byCreatedDate = emailSentHistoryRepository.findByCreatedDate(from, to);
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSentHistoryEntity entity : byCreatedDate) {
            dtoList.add(toDo(entity));
        }
        return dtoList;
    }

    private EmailHistoryDTO toDo(EmailSentHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
//        dto.setSentSmsTime(entity.getSentSmsTime());
//        dto.setProfileId(entity.getProfile().getId());
//        dto.setSentSms(entity.getSentSms());
        return dto;
    }

    public PageImpl<EmailHistoryDTO> getByPagination(Pageable pageable) {

        Page<EmailSentHistoryEntity> allHistory = emailSentHistoryRepository.findAllHistory(pageable);
        List<EmailSentHistoryEntity> content = allHistory.getContent();
        long totalElements = allHistory.getTotalElements();
        List<EmailHistoryDTO>dtoList=new LinkedList<>();
        for (EmailSentHistoryEntity entity : content) {
            dtoList.add(toDo(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public void create(RegistrationProfileDTO dto, String text) {
        EmailSentHistoryEntity entity = new EmailSentHistoryEntity();
        entity.setEmail(dto.getEmail());
        entity.setMessage(text);
        emailSentHistoryRepository.save(entity);
    }
}
