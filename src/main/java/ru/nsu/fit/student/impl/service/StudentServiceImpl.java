package ru.nsu.fit.student.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.student.impl.data.StudentLessonRepository;
import ru.nsu.fit.student.impl.data.StudentRepository;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentLessonRepository studentLessonRepository;
    private final StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void saveStudentLesson(StudentLesson studentLesson) {
        studentLessonRepository.save(studentLesson);    
    }
}
