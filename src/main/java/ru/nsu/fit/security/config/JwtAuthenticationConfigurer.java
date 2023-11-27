package ru.nsu.fit.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.nsu.fit.security.impl.domain.service.*;
import ru.nsu.fit.security.impl.domain.service.tokendeserializer.TokenStringDeserializer;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.AccessTokenJwsStringSerializer;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.RefreshTokenJweStringSerializer;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.TokenStringSerializer;
import ru.nsu.fit.security.impl.service.BlacklistedTokenService;
import ru.nsu.fit.security.port.TokenUrl;


public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private TokenStringSerializer refreshTokenSerializer;
    private TokenStringSerializer accessTokenSerializer;
    private TokenStringDeserializer accessTokenStringDeserializer;
    private TokenStringDeserializer refreshTokenStringDeserializer;

    private final BlacklistedTokenService blacklistedTokenService;
    private final RequestJwtTokensFilter requestJwtTokensFilter;
    private final RefreshTokenFilter refreshTokenFilter;

    public JwtAuthenticationConfigurer(BlacklistedTokenService blacklistedTokenService,
                                       RequestJwtTokensFilter requestJwtTokensFilter,
                                       RefreshTokenFilter refreshTokenFilter) {
        this.blacklistedTokenService = blacklistedTokenService;
        this.requestJwtTokensFilter = requestJwtTokensFilter;
        this.refreshTokenFilter = refreshTokenFilter;
    }

    @Override
    public void init(HttpSecurity builder) {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher(TokenUrl.TOKENS, HttpMethod.GET.name()));
        }
    }

    @Override
    public void configure(HttpSecurity builder) {
        requestJwtTokensFilter.setAccessTokenStringSerializer(accessTokenSerializer);
        requestJwtTokensFilter.setRefreshTokenStringSerializer(refreshTokenSerializer);

        var jwtAuthenticationFilter = new AuthenticationFilter(
                builder.getSharedObject(AuthenticationManager.class),
                new JwtAuthenticationConverter(accessTokenStringDeserializer, refreshTokenStringDeserializer)
        );
        jwtAuthenticationFilter
                .setSuccessHandler((request, response, authentication) -> CsrfFilter.skipRequest(request));
        jwtAuthenticationFilter
                .setFailureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_FORBIDDEN));


        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(new TokenAuthenticationUserDetailsService(blacklistedTokenService));

        refreshTokenFilter.setAccessTokenStringSerializer(accessTokenSerializer);

        var logoutFilter = new LogoutTokenFilter(blacklistedTokenService);

        builder.addFilterAfter(requestJwtTokensFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, CsrfFilter.class)
                .addFilterAfter(refreshTokenFilter, ExceptionTranslationFilter.class)
                .addFilterAfter(logoutFilter, ExceptionTranslationFilter.class)
                .authenticationProvider(authenticationProvider);
    }

    public JwtAuthenticationConfigurer accessTokenStringSerializer(AccessTokenJwsStringSerializer accessTokenJwsStringSerializer) {
        this.accessTokenSerializer = accessTokenJwsStringSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenStringSerializer(RefreshTokenJweStringSerializer refreshTokenJweStringSerializer) {
        this.refreshTokenSerializer = refreshTokenJweStringSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringDeserializer(TokenStringDeserializer accessTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenStringDeserializer(TokenStringDeserializer refreshTokenStringDeserializer) {
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
        return this;
    }
}
