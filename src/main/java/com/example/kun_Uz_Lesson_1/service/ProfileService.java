package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

}
