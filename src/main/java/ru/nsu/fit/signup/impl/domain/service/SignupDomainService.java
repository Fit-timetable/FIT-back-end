package ru.nsu.fit.signup.impl.domain.service;

import java.time.LocalDateTime;

import ru.nsu.fit.signup.impl.domain.model.entities.ConfirmCodeQuery;

public class SignupDomainService {
    public static ConfirmCodeQuery createConfirmCodeQuery(String email, String code, LocalDateTime expirationDate){
        return new ConfirmCodeQuery(email, code, expirationDate);
    }
}
