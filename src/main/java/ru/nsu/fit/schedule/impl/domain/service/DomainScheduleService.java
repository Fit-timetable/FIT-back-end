package ru.nsu.fit.schedule.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.api.dto.DayScheduleDto;
import ru.nsu.fit.schedule.api.dto.LessonScheduleDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.schedule.impl.domain.model.PinnedSchedule;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DomainScheduleService {
    private static final int CORRECT_DATE_FORMAT_LEN = 5;

    public PinnedSchedule createPinnedSchedule(Group group, Student student) {
        PinnedSchedule pinnedSchedule = new PinnedSchedule();
        pinnedSchedule.setGroup(group);
        pinnedSchedule.setStudent(student);
        return pinnedSchedule;
    }

    private LessonScheduleDto newLessonScheduleDto(Lesson lesson, String startTime) {
        return new LessonScheduleDto(
                lesson.getId(),
                lesson.getSubject().getName(),
                lesson.getLessonType(),
                startTime,
                lesson.getTeacher(),
                new LessonPlace(lesson.getRoom(), lesson.getMeetLink()),
                lesson.getLessonParity()
        );
    }

    private DayScheduleDto getDayScheduleByDayName(DayName dayName, WeekScheduleDto weekScheduleDto) {
        return switch (dayName) {
            case MONDAY -> weekScheduleDto.monday();
            case TUESDAY -> weekScheduleDto.tuesday();
            case WEDNESDAY -> weekScheduleDto.wednesday();
            case THURSDAY ->  weekScheduleDto.thursday();
            case FRIDAY -> weekScheduleDto.friday();
            case SATURDAY -> weekScheduleDto.saturday();
        };
    }

    public WeekScheduleDto getScheduleWithStudentLessons(WeekScheduleDto weekScheduleDto, List<StudentLesson> studentLessons) {
        for (StudentLesson studentLesson : studentLessons) {
            if (!studentLesson.getVisited()) {
                continue;
            }
            Lesson lesson = studentLesson.getLesson();

            DayScheduleDto dayScheduleDto = getDayScheduleByDayName(lesson.getDayName(), weekScheduleDto);
            LocalDateTime localDateTime = lesson.getStartTime();

            boolean isLessonAdded = false;
            List<LessonScheduleDto> lessonScheduleDtos = dayScheduleDto.lessons();
            for (LessonScheduleDto lessonScheduleDto : new ArrayList<>(lessonScheduleDtos)) {

                String startTime = lessonScheduleDto.startTime();
                String formattedStartTime = (startTime.length() == CORRECT_DATE_FORMAT_LEN - 1) ? "0" + startTime : startTime;

                LocalDateTime lessonStartDateTime = localDateTime.toLocalDate().atTime(LocalTime.parse(formattedStartTime));

                if (lessonStartDateTime.equals(localDateTime)) {
                    if (isLessonAdded) {
                        lessonScheduleDtos.remove(lessonScheduleDto);
                    }
                    else {
                        lessonScheduleDtos.set(
                                lessonScheduleDtos.indexOf(lessonScheduleDto),
                                newLessonScheduleDto(lesson, formattedStartTime)
                        );
                        isLessonAdded = true;
                    }
                }
            }
            if (!isLessonAdded) {
                lessonScheduleDtos.add(newLessonScheduleDto(lesson,
                        lesson.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                ));
            }
        }
        return weekScheduleDto;
    }
}
