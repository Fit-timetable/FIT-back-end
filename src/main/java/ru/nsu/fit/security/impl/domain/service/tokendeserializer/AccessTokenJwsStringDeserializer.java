package ru.nsu.fit.security.impl.domain.service.tokendeserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;

import java.text.ParseException;
import java.util.UUID;

public class AccessTokenJwsStringDeserializer implements TokenStringDeserializer {

    private final JWSVerifier jwsVerifier;

    public AccessTokenJwsStringDeserializer(JWSVerifier jwsVerifier) {
        this.jwsVerifier = jwsVerifier;
    }

    @Override
    public Token deserialize(String source) {
        try {
            var signedJWT = SignedJWT.parse(source);
            if (signedJWT.verify(jwsVerifier)) {
                var claimsSet = signedJWT.getJWTClaimsSet();
                return new Token(
                        UUID.fromString(claimsSet.getJWTID()),
                        TokenType.ACCESS,
                        claimsSet.getSubject(),
                        claimsSet.getStringListClaim("authorities"),
                        claimsSet.getIssueTime().toInstant(),
                        claimsSet.getExpirationTime().toInstant()
                );
            }
        } catch (ParseException | JOSEException e) {
            return null;
        }

        return null;
    }
}
