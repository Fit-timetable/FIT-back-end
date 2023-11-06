package ru.nsu.fit.lesson.impl.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.model.repositories.LessonRepository;
import ru.nsu.fit.lesson.impl.domain.service.DomainLessonService;
import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.domain.model.entities.Subject;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService{
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
}
