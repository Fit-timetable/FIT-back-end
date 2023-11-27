package ru.nsu.fit.security.impl.domain.service.tokendeserializer;

import ru.nsu.fit.security.impl.domain.model.Token;

public interface TokenStringDeserializer {
    Token deserialize(String source);
}
