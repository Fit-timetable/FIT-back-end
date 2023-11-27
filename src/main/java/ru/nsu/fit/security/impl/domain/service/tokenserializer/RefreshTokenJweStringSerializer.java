package ru.nsu.fit.security.impl.domain.service.tokenserializer;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import ru.nsu.fit.security.impl.domain.model.Token;

import java.util.Date;

public class RefreshTokenJweStringSerializer implements TokenStringSerializer {

    private final JWEEncrypter jweEncrypter;
    private final JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
    private final EncryptionMethod encryptionMethod = EncryptionMethod.A256GCM;

    public RefreshTokenJweStringSerializer(JWEEncrypter jweEncrypter) {
        this.jweEncrypter = jweEncrypter;
    }

    @Override
    public String serialize(Token token) {
        var jwsHeader = new JWEHeader.Builder(jweAlgorithm, encryptionMethod)
                .keyID(token.id().toString())
                .build();
        var claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();

        var encryptedJWT = new EncryptedJWT(jwsHeader, claimsSet);

        try {
            encryptedJWT.encrypt(jweEncrypter);

            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
