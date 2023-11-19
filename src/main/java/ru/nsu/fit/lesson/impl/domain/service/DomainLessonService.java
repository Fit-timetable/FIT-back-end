package ru.nsu.fit.lesson.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.lesson.impl.domain.model.entities.CanceledLesson;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DomainLessonService {
    public static Lesson mapping(LocalDateTime startTime,
                                 DayName dayName,
                                 String room,
                                 String meetLink,
                                 LessonType type,
                                 LessonParity parity,
                                 Subject subject) {

        return new Lesson(subject, startTime, dayName, room, meetLink, type, parity);
    }

    public static Lesson mapping(LocalDateTime startTime,
                                 DayName dayName,
                                 String teacher,
                                 String room,
                                 String meetLink,
                                 Lesson updatableLesson,
                                 Subject subject) {
        updatableLesson.update(subject, startTime, dayName, teacher, room, meetLink);

        return updatableLesson;
    }

    public static StudentLesson mapping(Lesson lesson, Student student){
        return new StudentLesson(lesson, student, true);
    }

    public static CanceledLesson mapping(Lesson lesson, Date date, StudentLesson studentLesson) {
        return new CanceledLesson(studentLesson.getStudent().getId(), lesson.getId(), date);
    }
}
