package ru.nsu.fit.lesson.api;

import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import java.time.LocalDate;

public interface LessonService {
    Lesson createLesson(LessonForm lessonForm);
    Lesson cancelLesson(Long id, LocalDate localDate);
    Lesson editLesson(Long id, Lesson lesson);
    Lesson deleteLesson(Long id);
}
