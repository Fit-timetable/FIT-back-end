package ru.nsu.fit.subject.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.subject.impl.domain.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
