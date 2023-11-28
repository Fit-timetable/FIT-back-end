package ru.nsu.fit.signup.impl.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.signup.impl.domain.model.entities.ConfirmCodeQuery;

public interface ConfirmCodeQueryRepository extends JpaRepository<ConfirmCodeQuery, Long>{
    Optional<ConfirmCodeQuery> findByEmail(String email);
}
