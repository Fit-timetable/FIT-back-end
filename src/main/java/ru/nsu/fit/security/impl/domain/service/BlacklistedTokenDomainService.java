package ru.nsu.fit.security.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.security.impl.domain.model.BlacklistedToken;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;

import java.time.ZoneId;

@Service
public class BlacklistedTokenDomainService {
    public BlacklistedToken create(Token token) {
        if (token.type() != TokenType.REFRESH) {
            throw new IllegalArgumentException();
        }

        return new BlacklistedToken(
                token.id(),
                token.expiresAt().atZone(ZoneId.systemDefault())
        );
    }
}
