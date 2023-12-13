package ru.nsu.fit.lesson.impl.domain.service;

import org.springframework.stereotype.Service;

import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.lesson.impl.domain.model.entities.CanceledLesson;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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

    public static LessonDetailsDto toLessonDetailsDTO(Lesson lesson, HomeworkResponseDto homeworkResponseDto, List<ResourceResponseDto> resources){
        LessonPlace lessonPlace = new LessonPlace(lesson.getRoom(), lesson.getMeetLink());
        
        return new LessonDetailsDto(lesson.getId(),
                getHHmm(lesson.getStartTime()),
                lesson.getSubject().getName(),
                lessonPlace,
                lesson.getTeacher(),
                homeworkResponseDto,
                resources);
    }

    public static String getHHmm(LocalDateTime dateTime){
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
