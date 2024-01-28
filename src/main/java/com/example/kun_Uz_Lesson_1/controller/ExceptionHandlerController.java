package com.example.kun_Uz_Lesson_1.controller;


import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.exp.ForbiddenException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {AppBadException.class})
    public ResponseEntity<?> handle(AppBadException appBadException) {
        return ResponseEntity.badRequest().body(appBadException.getMessage());
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<?> handle(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handle(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    private ResponseEntity<?> handle(JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
