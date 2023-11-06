package ru.nsu.fit.schedule.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.domain.model.PinnedSchedule;

import java.util.Optional;

@Repository
public interface PinnedScheduleRepository extends JpaRepository<PinnedSchedule, Long> {
    Optional<PinnedSchedule> findByStudentId(Long studentId);
}
