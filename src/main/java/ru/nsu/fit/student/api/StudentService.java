package ru.nsu.fit.student.api;

import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

public interface StudentService {
    Student getStudent(Long id);

    void saveStudentLesson(StudentLesson studentLesson);
}
