package ru.nsu.fit.schedule.impl.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.data.entities.LessonEntity;
import ru.nsu.fit.schedule.impl.data.entities.StudentEntity;
import ru.nsu.fit.schedule.impl.data.entities.StudentLessonEntity;

import java.util.List;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLessonEntity, Long> {
    List<StudentLessonEntity> findByStudentEntity_Id(Long studentId);
    StudentLessonEntity findByStudentEntityAndLessonEntity(StudentEntity studentEntity, LessonEntity lessonEntity);
}
