package ru.nsu.fit.student.impl.service;

import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.student.impl.domain.model.entities.Student;
import ru.nsu.fit.student.impl.domain.model.entities.StudentLesson;
import ru.nsu.fit.student.impl.domain.model.repositories.StudentLessonRepository;
import ru.nsu.fit.student.impl.domain.model.repositories.StudentRepository;

public class StudentServiceImpl implements StudentService {
    private StudentLessonRepository studentLessonRepository;
    private StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void saveStudentLesson(StudentLesson studentLesson) {
        studentLessonRepository.save(studentLesson);    
    }
}
