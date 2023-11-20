package ru.nsu.fit.email.api;

import ru.nsu.fit.email.api.dto.ConfirmSignupDTO;

public interface ConfirmSignupService {
    void createStudent(ConfirmSignupDTO confirmSignupDTO);
} 