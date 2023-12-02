package ru.nsu.fit.email.impl.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.RequestSignupService;
import ru.nsu.fit.email.impl.domain.service.DomainEmailService;
import ru.nsu.fit.signup.api.SignupRequestedEvent;

@Service
@AllArgsConstructor
public class RequestSignupServiceImpl implements RequestSignupService{
    private EmailService emailService;
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void sendConfirmMessage(String email) {
        String confirmCode = DomainEmailService.generateConfirmCode();
        String subject = "CONFIRM EMAIL";
        emailService.sendMail(email, subject, confirmCode);

        eventPublisher.publishEvent(new SignupRequestedEvent(this, email, confirmCode));
    }
}
