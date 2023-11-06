package ru.nsu.fit.lesson.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
