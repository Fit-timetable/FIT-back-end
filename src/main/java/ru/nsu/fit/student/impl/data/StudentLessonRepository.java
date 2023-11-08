package ru.nsu.fit.student.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
    List<StudentLesson> findByStudentId(Long studentId);
}