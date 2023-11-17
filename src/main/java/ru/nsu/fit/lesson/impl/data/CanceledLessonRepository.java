package ru.nsu.fit.lesson.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.lesson.impl.domain.model.entities.CanceledLesson;

public interface CanceledLessonRepository extends JpaRepository<CanceledLesson, Long> {
}