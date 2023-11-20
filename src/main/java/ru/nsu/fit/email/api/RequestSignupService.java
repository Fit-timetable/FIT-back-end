package ru.nsu.fit.email.api;

public interface RequestSignupService {
    void sendConfirmMessage(String email);

    String generateConfirmCode();
}
