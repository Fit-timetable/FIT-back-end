package ru.nsu.fit.student.impl.domain.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.student.impl.domain.model.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
