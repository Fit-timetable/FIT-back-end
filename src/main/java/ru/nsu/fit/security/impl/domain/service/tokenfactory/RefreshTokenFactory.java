package ru.nsu.fit.security.impl.domain.service.tokenfactory;


import org.springframework.security.core.Authentication;
import ru.nsu.fit.security.impl.domain.model.Token;

public interface RefreshTokenFactory {
    Token create(Authentication authentication);
}
