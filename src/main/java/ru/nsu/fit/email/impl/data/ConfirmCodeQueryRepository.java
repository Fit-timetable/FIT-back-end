package ru.nsu.fit.email.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.email.impl.domain.model.entities.ConfirmCodeQuery;

public interface ConfirmCodeQueryRepository extends JpaRepository<ConfirmCodeQuery, Long>{
    ConfirmCodeQuery findByEmail(String email);
}
