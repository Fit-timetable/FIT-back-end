package ru.nsu.fit.schedule.impl.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.data.entities.GroupStudent;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

}
