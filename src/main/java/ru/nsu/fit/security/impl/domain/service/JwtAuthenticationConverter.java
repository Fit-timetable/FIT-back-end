package ru.nsu.fit.security.impl.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import ru.nsu.fit.security.impl.domain.service.tokendeserializer.TokenStringDeserializer;

public class JwtAuthenticationConverter implements AuthenticationConverter {

    private final TokenStringDeserializer accessTokenStringDeserializer;
    private final TokenStringDeserializer refreshTokenStringDeserializer;

    private final static String BEARER_HEADER_BEGINNING = "Bearer ";

    public JwtAuthenticationConverter(TokenStringDeserializer accessTokenStringDeserializer,
                                      TokenStringDeserializer refreshTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BEARER_HEADER_BEGINNING)) {
            var token = authorization.substring(BEARER_HEADER_BEGINNING.length());

            var accessToken = accessTokenStringDeserializer.deserialize(token);
            if (accessToken != null) {
                return new PreAuthenticatedAuthenticationToken(accessToken, token);
            }

            var refreshToken = refreshTokenStringDeserializer.deserialize(token);
            if (refreshToken != null) {
                return new PreAuthenticatedAuthenticationToken(refreshToken, token);
            }
        }

        return null;
    }
}
