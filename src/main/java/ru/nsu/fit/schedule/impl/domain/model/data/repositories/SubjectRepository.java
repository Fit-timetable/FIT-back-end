package ru.nsu.fit.schedule.impl.domain.model.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.domain.model.data.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
