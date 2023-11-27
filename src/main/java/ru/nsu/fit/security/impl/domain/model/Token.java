package ru.nsu.fit.security.impl.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Token(
        UUID id,
        TokenType type,
        String subject,
        List<String> authorities,
        Instant createdAt,
        Instant expiresAt) {
}
