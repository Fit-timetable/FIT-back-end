package ru.nsu.fit.group.impl.domain.model.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.group.impl.domain.model.data.entities.GroupStudent;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

}
