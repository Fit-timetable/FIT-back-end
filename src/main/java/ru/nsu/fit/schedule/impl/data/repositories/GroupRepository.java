package ru.nsu.fit.schedule.impl.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.data.entities.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByNumber(String number);
}
