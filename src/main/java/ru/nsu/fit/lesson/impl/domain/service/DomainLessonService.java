package ru.nsu.fit.lesson.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class DomainLessonService {
    public static Lesson mapping(LessonForm lessonForm, Subject subject){
        Lesson lesson = new Lesson();
        lesson.setDayName(lessonForm.date().weekDay());
        lesson.setStartTime(lessonForm.date().startTime());
        lesson.setRoom(lessonForm.place().room());
        lesson.setTeacher(null);
        lesson.setMeetLink(lessonForm.place().meetLink());
        lesson.setLessonType(lessonForm.type());
        lesson.setLessonParity(lessonForm.parity());
        lesson.setSubject(subject); 
        lesson.setGroup(null);

        return lesson;
    }

    public static Lesson mapping(Lesson newLesson, Lesson existingLesson) {
        updateField(newLesson.getDayName(), existingLesson::setDayName);
        updateField(newLesson.getStartTime(), existingLesson::setStartTime);
        updateField(newLesson.getRoom(), existingLesson::setRoom);
        updateField(newLesson.getTeacher(), existingLesson::setTeacher);
        updateField(newLesson.getMeetLink(), existingLesson::setMeetLink);
        updateField(newLesson.getLessonType(), existingLesson::setLessonType);
        updateField(newLesson.getLessonParity(), existingLesson::setLessonParity);
        updateField(newLesson.getSubject(), existingLesson::setSubject);
        updateField(newLesson.getGroup(), existingLesson::setGroup);

        return existingLesson;
    }

    public static StudentLesson mapping(Lesson lesson, Student student){
        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setLesson(lesson);
        studentLesson.setStudent(student);
        studentLesson.setVisited(true);

        return studentLesson;
    }

    private static <T> void updateField(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}
