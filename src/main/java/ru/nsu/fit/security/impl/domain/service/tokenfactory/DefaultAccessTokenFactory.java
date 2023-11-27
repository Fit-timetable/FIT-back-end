package ru.nsu.fit.security.impl.domain.service.tokenfactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;

import java.time.Duration;
import java.time.Instant;

@Component
public class DefaultAccessTokenFactory implements AccessTokenFactory {

    @Value("#{T(java.time.Duration).parse('${access-token-ttl}')}")
    private Duration tokenTtl;

    @Override
    public Token create(Token refreshToken) {
        var now = Instant.now();
        return new Token(
                refreshToken.id(),
                TokenType.ACCESS,
                refreshToken.subject(),
                refreshToken.authorities().stream()
                        .filter(authority -> authority.startsWith("GRANT_"))
                        .map(authority -> authority.substring(6))
                        .toList(),
                now,
                now.plus(tokenTtl)
        );
    }
}
