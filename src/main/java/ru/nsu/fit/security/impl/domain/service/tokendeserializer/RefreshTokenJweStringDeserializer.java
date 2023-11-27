package ru.nsu.fit.security.impl.domain.service.tokendeserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import ru.nsu.fit.security.impl.domain.model.Token;
import ru.nsu.fit.security.impl.domain.model.TokenType;

import java.text.ParseException;
import java.util.UUID;

public class RefreshTokenJweStringDeserializer implements TokenStringDeserializer{
    private final JWEDecrypter jweDecrypter;

    public RefreshTokenJweStringDeserializer(JWEDecrypter jweDecrypter) {
        this.jweDecrypter = jweDecrypter;
    }

    @Override
    public Token deserialize(String source) {
        try {
            var encryptedJWT = EncryptedJWT.parse(source);
            encryptedJWT.decrypt(jweDecrypter);
            var claimsSet = encryptedJWT.getJWTClaimsSet();
            return new Token(
                    UUID.fromString(claimsSet.getJWTID()),
                    TokenType.REFRESH,
                    claimsSet.getSubject(),
                    claimsSet.getStringListClaim("authorities"),
                    claimsSet.getIssueTime().toInstant(),
                    claimsSet.getExpirationTime().toInstant()
            );
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
