package com.example.kun_Uz_Lesson_1.utils;

import java.util.Random;

public class RandomUtil {
    private static Random random = new Random();

    public static String getRandomCode() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            password.append(random.nextInt(1, 9));
        }
        return password.toString();
    }
}
