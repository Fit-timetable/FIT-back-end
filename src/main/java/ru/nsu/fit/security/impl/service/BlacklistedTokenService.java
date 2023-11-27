package ru.nsu.fit.security.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.security.impl.data.BlacklistedTokenRepository;
import ru.nsu.fit.security.impl.domain.model.BlacklistedToken;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.service.BlacklistedTokenDomainService;

import java.util.Optional;

@Service
public class BlacklistedTokenService {

    private final BlacklistedTokenRepository repository;
    private final BlacklistedTokenDomainService domainService;

    @Autowired
    public BlacklistedTokenService(BlacklistedTokenRepository repository,
                                   BlacklistedTokenDomainService domainService) {
        this.repository = repository;
        this.domainService = domainService;
    }

    public void add(Token token) {
        BlacklistedToken blacklistedToken = domainService.create(token);
        repository.save(blacklistedToken);
    }

    public boolean isBlacklisted(Token token) {
        Optional<BlacklistedToken> blacklistedToken = repository.findById(token.id());
        return blacklistedToken.isPresent();
    }
}
