package ru.nsu.fit.email.impl.domain.service;

import java.util.Random;

public class DomainEmailService {
     public static String generateConfirmCode() {
        Random random = new Random();
        StringBuilder confirmCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            confirmCode.append(random.nextInt(10));
        }

        return confirmCode.toString();
    }
}
