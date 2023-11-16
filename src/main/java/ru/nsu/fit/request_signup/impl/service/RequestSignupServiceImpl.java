package ru.nsu.fit.request_signup.impl.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.request_signup.api.RequestSignupService;

@Service
@AllArgsConstructor
public class RequestSignupServiceImpl implements RequestSignupService{
    private EmailService emailService;

    @Override
    public void sendConfirmMessage(String email) {
        emailService.sendConfirmationCode(email);
    }    
}
