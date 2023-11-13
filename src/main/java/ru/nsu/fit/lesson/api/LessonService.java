package ru.nsu.fit.lesson.api;

import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;

public interface LessonService {
    Lesson createLesson(LessonForm lessonForm);

    LessonDetailsDto getLesson(Long lessonId);
}
