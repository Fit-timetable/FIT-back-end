package ru.nsu.fit.schedule.impl.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.data.entities.LessonEntity;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

}
