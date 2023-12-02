package ru.nsu.fit.student.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

import java.util.List;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
    List<StudentLesson> findByStudentId(Long studentId);
    List<StudentLesson> findByLessonId(Long lessonId);
}