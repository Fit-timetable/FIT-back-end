package ru.nsu.fit.group.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.group.impl.domain.model.GroupStudent;

import java.util.Optional;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {
    Optional<GroupStudent> findByStudentId(Long studentId);
}
