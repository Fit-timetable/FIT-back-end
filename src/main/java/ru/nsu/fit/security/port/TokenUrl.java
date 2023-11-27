package ru.nsu.fit.security.port;

public interface TokenUrl {
    String TOKENS = "/jwt/tokens";
    String TOKEN_REFRESH = "/jwt/refresh";
    String TOKEN_LOGOUT = "/jwt/logout";
}
