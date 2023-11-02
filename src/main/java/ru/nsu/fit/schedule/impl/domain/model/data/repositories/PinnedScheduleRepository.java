package ru.nsu.fit.schedule.impl.domain.model.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.domain.model.data.entities.PinnedSchedule;

@Repository
public interface PinnedScheduleRepository extends JpaRepository<PinnedSchedule, Long> {

}

