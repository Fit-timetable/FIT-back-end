package ru.nsu.fit.student.api;

import ru.nsu.fit.student.impl.domain.model.entities.Student;
import ru.nsu.fit.student.impl.domain.model.entities.StudentLesson;

public interface StudentService {
    Student getStudent(Long id);

    void saveStudentLesson(StudentLesson studentLesson);
}
