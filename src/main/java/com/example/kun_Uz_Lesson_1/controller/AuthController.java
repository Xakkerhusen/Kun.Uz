package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.Auth;
import com.example.kun_Uz_Lesson_1.dto.SmsSendDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.dto.profile.RegistrationProfileDTO;
import com.example.kun_Uz_Lesson_1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth auth) {
        return ResponseEntity.ok(authService.auth(auth));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> login(@RequestBody RegistrationProfileDTO auth) {
        return ResponseEntity.ok(authService.registration(auth));
    }

    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<Profile> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }

    @PostMapping("/verification/phone/")
    public ResponseEntity<?> SmsVerification(@RequestBody SmsSendDTO dto) {
        return ResponseEntity.ok(authService.smsVerification(dto));
    }


}
