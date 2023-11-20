package ru.nsu.fit.email.api.dto;

public record ConfirmSignupDTO(
    String email,
    String password,
    int confirmationCode
) 
{}
