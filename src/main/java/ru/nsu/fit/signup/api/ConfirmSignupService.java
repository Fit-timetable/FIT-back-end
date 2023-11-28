package ru.nsu.fit.signup.api;

import java.time.LocalDateTime;

import ru.nsu.fit.signup.api.dto.ConfirmSignupDTO;

public interface ConfirmSignupService {
    void createStudent(ConfirmSignupDTO confirmSignupDTO);

    void pushConfirmCodeToQuery(String email, String code, LocalDateTime expirationDate);
} 