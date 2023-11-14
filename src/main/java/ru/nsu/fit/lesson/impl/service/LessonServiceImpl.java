package ru.nsu.fit.lesson.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.impl.data.LessonRepository;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.service.DomainLessonService;
import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private SubjectService subjectService;
    private StudentService studentService;

    @Override
    public Lesson createLesson(LessonForm lessonForm) {
        Subject subject = subjectService.getSubject(lessonForm.subjectId());
        Lesson lesson = lessonRepository.save(DomainLessonService.mapping(lessonForm, subject));

        studentService.saveStudentLesson(DomainLessonService.mapping(lesson, studentService.getStudent(lessonForm.studentId())));

        return lesson;
    }

    @Override
    public Lesson cancelLesson(Long id, LocalDate localDate) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();

        if (lesson.getStartTime().toLocalDate().equals(localDate)) {
            lessonRepository.delete(lesson);
            return lesson;
        }
        return null;
    }

    @Override
    public Lesson editLesson(Long id, Lesson lesson) {
        Lesson existingLesson = lessonRepository.findById(id).orElseThrow();

        lessonRepository.save(DomainLessonService.mapping(lesson, existingLesson));

        return existingLesson;
    }

    @Override
    public Lesson deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        lessonRepository.delete(lesson);

        return lesson;
    }
}
