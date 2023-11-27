package ru.nsu.fit.security.impl.domain.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.fit.security.impl.domain.model.TokenUser;
import ru.nsu.fit.security.impl.service.BlacklistedTokenService;
import ru.nsu.fit.security.port.TokenUrl;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class LogoutTokenFilter extends OncePerRequestFilter {
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(TokenUrl.TOKEN_LOGOUT, HttpMethod.POST.name());
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final BlacklistedTokenService blacklistedTokenService;

    @Autowired
    public LogoutTokenFilter(BlacklistedTokenService blacklistedTokenService) {
        this.blacklistedTokenService = blacklistedTokenService;
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
                        context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("JWT_LOGOUT"))) {
                    blacklistedTokenService.add(user.getToken());
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    return;
                }
            }

            throw new AccessDeniedException("User must be authenticated with JWT");
        }

        filterChain.doFilter(request, response);
    }
}
