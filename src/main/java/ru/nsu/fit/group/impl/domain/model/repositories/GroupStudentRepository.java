package ru.nsu.fit.group.impl.domain.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.group.impl.domain.model.entities.GroupStudent;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

}