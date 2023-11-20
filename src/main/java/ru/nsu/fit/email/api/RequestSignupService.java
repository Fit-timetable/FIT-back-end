package ru.nsu.fit.email.api;

import java.time.LocalDateTime;

public interface RequestSignupService {
    void sendConfirmMessage(String email);

    String generateConfirmCode();

    void createConfirmCodeQuery(String email, String code, LocalDateTime expirationDate);
}
