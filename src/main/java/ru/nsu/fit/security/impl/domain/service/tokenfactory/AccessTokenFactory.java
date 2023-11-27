package ru.nsu.fit.security.impl.domain.service.tokenfactory;

import ru.nsu.fit.security.impl.domain.model.Token;

public interface AccessTokenFactory {
    Token create(Token refreshToken);
}
