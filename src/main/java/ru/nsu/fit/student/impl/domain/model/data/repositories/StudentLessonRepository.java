package ru.nsu.fit.student.impl.domain.model.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.lesson.impl.domain.model.data.entities.Lesson;
import ru.nsu.fit.student.impl.domain.model.data.entities.Student;
import ru.nsu.fit.student.impl.domain.model.data.entities.StudentLesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
    Optional<StudentLesson> findByStudentAndLesson(Student student, Lesson lesson);
    List<StudentLesson> findByStudent_Id(Long studentId);
}
