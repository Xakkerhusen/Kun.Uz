package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import com.example.kun_Uz_Lesson_1.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InitService {
    @Autowired
    private ProfileRepository profileRepository;

    public void initAdmin() {
        String adminEmail = "husen@mail.ru";
        Optional<ProfileEntity> optional = profileRepository.findByEmail(adminEmail);
        if (optional.isPresent()) {
            return;
        }
        // create admin
        ProfileEntity admin = new ProfileEntity();
        admin.setName("Husen");
        admin.setSurname("Teshayev");
        admin.setEmail(adminEmail);
        admin.setStatus(ProfileStatus.ACTIVE);
        admin.setRole(ProfileRole.ROLE_ADMIN);
        admin.setPassword(MD5Util.encode("1999"));
        profileRepository.save(admin);
    }




}
