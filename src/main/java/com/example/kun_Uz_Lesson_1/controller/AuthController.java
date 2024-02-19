package com.example.kun_Uz_Lesson_1.controller;

import com.example.kun_Uz_Lesson_1.dto.Auth;
import com.example.kun_Uz_Lesson_1.dto.SmsSendDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.dto.profile.RegistrationProfileDTO;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
//    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<?> login(@RequestBody Auth auth,
                                   @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
//        log.trace("Login In Trace");
//        log.debug("Login In Debug");
//        log.warn("Login {} ", auth.getEmail());
//        log.error("Login {} ", auth.getEmail());
        log.info("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.auth(auth,language));
    }

    @PostMapping("/registration")
    @Operation( summary = "Api for registration", description = "this api used for authorization")
    public ResponseEntity<?> login( @Valid @RequestBody RegistrationProfileDTO auth,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("registration {}", auth.getEmail());
        return ResponseEntity.ok(authService.registration(auth,language));
    }

    @GetMapping("/verification/email/{jwt}")
    @Operation( summary = "Api for verification by email", description = "this api used for authorization")
    public ResponseEntity<Profile> emailVerification(@PathVariable("jwt") String jwt,
                                                     @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("verification {}", jwt);
        return ResponseEntity.ok(authService.emailVerification(jwt,language));
    }

    @PostMapping("/verification/phone")
    @Operation( summary = "Api for verification by phone number", description = "this api used for authorization")
    public ResponseEntity<?> SmsVerification(@RequestBody SmsSendDTO dto,
                                             @RequestHeader(value = "Accept-Language",defaultValue = "UZ") Language language) {
        log.info("verification {}", dto.getPhoneNumber());
        return ResponseEntity.ok(authService.smsVerification(dto,language));
    }


}
