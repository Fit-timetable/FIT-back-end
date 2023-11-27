package ru.nsu.fit.security.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.nsu.fit.security.impl.domain.service.RefreshTokenFilter;
import ru.nsu.fit.security.impl.domain.service.RequestJwtTokensFilter;
import ru.nsu.fit.security.impl.domain.service.tokendeserializer.AccessTokenJwsStringDeserializer;
import ru.nsu.fit.security.impl.domain.service.tokendeserializer.RefreshTokenJweStringDeserializer;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.AccessTokenJwsStringSerializer;
import ru.nsu.fit.security.impl.domain.service.tokenserializer.RefreshTokenJweStringSerializer;
import ru.nsu.fit.security.impl.service.BlacklistedTokenService;
import ru.nsu.fit.student.api.StudentService;

import java.text.ParseException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
            @Value("${access-token-key}") String accessTokenKey,
            @Value("${refresh-token-key}") String refreshTokenKey,
            BlacklistedTokenService blacklistedTokenService,
            RequestJwtTokensFilter requestJwtTokensFilter,
            RefreshTokenFilter refreshTokenFilter
    ) throws ParseException, JOSEException {
        return new JwtAuthenticationConfigurer(blacklistedTokenService, requestJwtTokensFilter, refreshTokenFilter)
                .accessTokenStringSerializer(new AccessTokenJwsStringSerializer(
                        new MACSigner(OctetSequenceKey.parse(accessTokenKey))
                ))
                .refreshTokenStringSerializer(new RefreshTokenJweStringSerializer(
                        new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey))
                ))
                .accessTokenStringDeserializer(new AccessTokenJwsStringDeserializer(
                        new MACVerifier(OctetSequenceKey.parse(accessTokenKey))
                ))
                .refreshTokenStringDeserializer(new RefreshTokenJweStringDeserializer(
                        new DirectDecrypter(OctetSequenceKey.parse(refreshTokenKey))
                ));
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationConfigurer jwtAuthenticationConfigurer,
                                                   CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.apply(jwtAuthenticationConfigurer);

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(StudentService studentService) {
        return username -> {
            var user = studentService.getByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .build();
        };
    }
}
