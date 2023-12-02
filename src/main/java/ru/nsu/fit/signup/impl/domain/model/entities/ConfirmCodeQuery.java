package ru.nsu.fit.signup.impl.domain.model.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_code_query")
@NoArgsConstructor
public class ConfirmCodeQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    public ConfirmCodeQuery(String email, String code, LocalDateTime expirationDate) {
        this.email = email;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }
}
