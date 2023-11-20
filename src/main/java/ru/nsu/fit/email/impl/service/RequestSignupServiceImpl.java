package ru.nsu.fit.email.impl.service;

import java.util.Random;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.RequestSignupService;
import ru.nsu.fit.email.impl.data.ConfirmCodeQueryRepository;
import ru.nsu.fit.email.impl.domain.model.entities.ConfirmCodeQuery;

@Service
@AllArgsConstructor
public class RequestSignupServiceImpl implements RequestSignupService{
    private EmailService emailService;
    private final ConfirmCodeQueryRepository confirmCodeQueryRepository;

    @Override
    public void sendConfirmMessage(String email) {
        String confirmCode = generateConfirmCode();
        String subject = "CONFIRM EMAIL";
        emailService.sendMail(email, subject, confirmCode);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusMinutes(5);

        createConfirmCodeQuery(email, confirmCode, futureTime);
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

    @Override
    public void createConfirmCodeQuery(String email, String code, LocalDateTime expirationDate) {
        ConfirmCodeQuery confirmCodeQuery = new ConfirmCodeQuery(email, code, expirationDate);
        confirmCodeQueryRepository.save(confirmCodeQuery);
    }   
}
