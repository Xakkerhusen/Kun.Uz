package com.example.kun_Uz_Lesson_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommonLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private InitService initService;

    @Override
    public void run(String... args) throws Exception {
        initService.initAdmin();
    }

}
