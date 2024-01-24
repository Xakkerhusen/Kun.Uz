package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.Auth;
import com.example.kun_Uz_Lesson_1.dto.Profile;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import com.example.kun_Uz_Lesson_1.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile auth(Auth auth){
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(auth.getEmail(), MD5Util.encode(auth.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }

        ProfileEntity entity = optional.get();

        Profile dto = new Profile();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());

        return dto;
    }
}
