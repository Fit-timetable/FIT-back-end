package ru.nsu.fit.lesson.impl.domain.service;

import org.springframework.stereotype.Service;

import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.subject.impl.domain.model.entities.Subject;

@Service
public class LessonFormMapper {
    public static Lesson mapping(LessonForm lessonForm, Subject subject, Group group){
        Lesson lesson = new Lesson();
        lesson.setDayName(lessonForm.date().weekDay());
        lesson.setStartTime(lessonForm.date().startTime());
        lesson.setRoom(lessonForm.place().room());
        lesson.setTeacher(null);
        lesson.setMeetLink(lessonForm.place().meetLink());
        lesson.setLessonType(lessonForm.type());
        lesson.setLessonParity(lessonForm.parity());
        lesson.setSubject(subject); 
        lesson.setGroup(group);

        return lesson;
    }
}
