package ru.nsu.fit.security.impl.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "blacklisted_token")
@Entity
public class BlacklistedToken {

    /**
     * Идентификатор
     */
    @Id
    private UUID id;

    /**
     * Дата истечения токена
     */
    private ZonedDateTime keepUntil;

    @Deprecated
    protected BlacklistedToken() {
    }

    public BlacklistedToken(UUID id, ZonedDateTime keepUntil) {
        this.id = Objects.requireNonNull(id);
        this.keepUntil = Objects.requireNonNull(keepUntil);
    }

    public UUID getId() {
        return id;
    }

    public ZonedDateTime getKeepUntil() {
        return keepUntil;
    }
}
