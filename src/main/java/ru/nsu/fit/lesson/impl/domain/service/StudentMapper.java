package ru.nsu.fit.lesson.impl.domain.service;

import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.student.impl.domain.model.entities.Student;
import ru.nsu.fit.student.impl.domain.model.entities.StudentLesson;

public class StudentMapper {
    public static StudentLesson mapping(Lesson lesson, Student student){
        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setLesson(lesson);
        studentLesson.setStudent(student);
        studentLesson.setVisited(true);

        return studentLesson;
    } 
}
