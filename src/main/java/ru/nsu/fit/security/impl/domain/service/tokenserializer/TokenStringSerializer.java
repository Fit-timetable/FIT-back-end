package ru.nsu.fit.security.impl.domain.service.tokenserializer;

import ru.nsu.fit.security.impl.domain.model.Token;

public interface TokenStringSerializer {
    String serialize(Token token);
}
