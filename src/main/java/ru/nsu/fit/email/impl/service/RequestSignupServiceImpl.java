package ru.nsu.fit.email.impl.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.RequestSignupService;

@Service
@AllArgsConstructor
public class RequestSignupServiceImpl implements RequestSignupService{
    private EmailService emailService;

    @Override
    public void sendConfirmMessage(String email) {
        String confirmCode = generateConfirmCode();
        String subject = "CONFIRM EMAIL";
        emailService.sendMail(email, subject, confirmCode);
    }

    @Override
    public String generateConfirmCode() {
        Random random = new Random();
        StringBuilder confirmCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            confirmCode.append(random.nextInt(10));
        }

        return confirmCode.toString();
    }   
}
