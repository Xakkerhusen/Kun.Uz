package com.example.kun_Uz_Lesson_1.controller;


import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.exp.ForbiddenException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {AppBadException.class})
    public ResponseEntity<?> handle(AppBadException appBadException) {
        log.error(appBadException.getMessage());
        return ResponseEntity.badRequest().body(appBadException.getMessage());
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<?> handle(ForbiddenException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handle(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        log.error(runtimeException.toString());
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    private ResponseEntity<?> handle(JwtException e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
