package ru.nsu.fit.signup.api.dto;

public record ConfirmSignupDTO(
    String email,
    String password,
    Integer confirmationCode
) 
{}
