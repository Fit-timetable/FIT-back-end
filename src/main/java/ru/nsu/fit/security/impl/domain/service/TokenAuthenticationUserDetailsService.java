package ru.nsu.fit.security.impl.domain.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;
import ru.nsu.fit.security.impl.domain.model.TokenUser;
import ru.nsu.fit.security.impl.service.BlacklistedTokenService;

import java.time.Instant;

public class TokenAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final BlacklistedTokenService blacklistedTokenService;

    public TokenAuthenticationUserDetailsService(BlacklistedTokenService blacklistedTokenService) {
        this.blacklistedTokenService = blacklistedTokenService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken) throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            boolean isTokenBlacklisted = (token.type() == TokenType.REFRESH && blacklistedTokenService.isBlacklisted(token));
            return new TokenUser(
                    token.subject(),
                    "nopassword",
                    true,
                    true,
                    token.expiresAt().isAfter(Instant.now()) && !isTokenBlacklisted,
                    true,
                    token.authorities()
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList(),
                    token);
        }

        throw new UsernameNotFoundException("Principal must be of type token");
    }
}
