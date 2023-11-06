package ru.nsu.fit.subject.impl.domain.model.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.subject.impl.domain.model.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
