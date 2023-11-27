package ru.nsu.fit.student.api;

import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

import java.util.Optional;

public interface StudentService {
    Student getStudent(Long id);

    Optional<Student> getByEmail(String email);

    void saveStudentLesson(StudentLesson studentLesson);
}
