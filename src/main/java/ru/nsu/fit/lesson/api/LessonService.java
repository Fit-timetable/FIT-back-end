package ru.nsu.fit.lesson.api;

import ru.nsu.fit.lesson.api.dto.EditLessonDto;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;

import java.util.Date;

public interface LessonService {
    Lesson createLesson(LessonForm lessonForm);

    void cancelLesson(Long id, Date date);

    void editLesson(Long id, EditLessonDto editLessonDto);

    void deleteLesson(Long id);

    LessonDetailsDto getLessonDetails(Long lessonId);
}
