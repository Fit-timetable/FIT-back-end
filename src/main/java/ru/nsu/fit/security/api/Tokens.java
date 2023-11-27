package ru.nsu.fit.security.api;

public record Tokens(
        String accessToken,
        String accessTokenExpiry,
        String refreshToken,
        String refreshTokenExpiry) {
}
