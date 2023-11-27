package ru.nsu.fit.security.impl.domain.service.tokenfactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;

@Component
public class DefaultRefreshTokenFactory implements RefreshTokenFactory {

    @Value("#{T(java.time.Duration).parse('${refresh-token-ttl}')}")
    private Duration tokenTtl;

    @Override
    public Token create(Authentication authentication) {
        var authorities = new LinkedList<String>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");
        authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> "GRANT_" + authority)
                .forEach(authorities::add);

        var now = Instant.now();
        return new Token(
                UUID.randomUUID(),
                TokenType.REFRESH,
                authentication.getName(),
                authorities,
                now,
                now.plus(tokenTtl)
        );
    }
}
