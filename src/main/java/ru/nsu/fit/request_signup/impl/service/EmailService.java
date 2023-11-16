package ru.nsu.fit.request_signup.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    public String sendConfirmationCode(String toEmail) {
        String confirmCode = generateConfirmCode();

        SimpleMailMessage message = new SimpleMailMessage(); 

        message.setFrom(username);
        message.setTo(toEmail); 
        message.setSubject("Confirm"); 
        message.setText(confirmCode);
        emailSender.send(message);

        return confirmCode;
    }

    private String generateConfirmCode() {
        Random random = new Random();
        StringBuilder confirmCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            confirmCode.append(random.nextInt(10));
        }

        return confirmCode.toString();
    }
}
