package com.example.kun_Uz_Lesson_1.utils;


import com.example.kun_Uz_Lesson_1.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {

        public static CustomUserDetails getCurrentUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName(); // username
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails;
        }

    }

