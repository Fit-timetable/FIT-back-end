package ru.nsu.fit.security.impl.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.fit.security.api.Tokens;
import ru.nsu.fit.security.impl.domain.model.TokenUser;
import ru.nsu.fit.security.impl.domain.service.tokenfactory.AccessTokenFactory;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.TokenStringSerializer;
import ru.nsu.fit.security.port.TokenUrl;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(TokenUrl.TOKEN_REFRESH, HttpMethod.POST.name());
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final AccessTokenFactory accessTokenFactory;
    private TokenStringSerializer accessTokenStringSerializer = Object::toString;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RefreshTokenFilter(AccessTokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            if (securityContextRepository.containsContext(request)) {
                var context = securityContextRepository.loadDeferredContext(request).get();
                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
                        context.getAuthentication().getPrincipal() instanceof TokenUser user &&
                        context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("JWT_REFRESH"))) {
                    var accessToken = accessTokenFactory.create(user.getToken());

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(response.getWriter(),
                            new Tokens(
                                    accessTokenStringSerializer.serialize(accessToken),
                                    accessToken.expiresAt().toString(),
                                    null,
                                    null
                            )
                    );
                    return;
                }
            }

            throw new AccessDeniedException("User must be authenticated with JWT");
        }

        filterChain.doFilter(request, response);
    }

    public void setAccessTokenStringSerializer(TokenStringSerializer accessTokenSerializer) {
        this.accessTokenStringSerializer = accessTokenSerializer;
    }
}
