package ru.nsu.fit;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.lesson.impl.domain.model.*;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.api.dto.*;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.time.*;
import java.util.*;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.fit.schedule.impl.domain.model.DayName.*;

public class GetScheduleWithStudentLessonsTests {

    @Test
    public void testGetScheduleWithStudentLessons_emptyStudentLessons() {
        DomainScheduleService domainScheduleService = new DomainScheduleService();
        List<StudentLesson> studentLessons = new ArrayList<>();

        WeekScheduleDto weekScheduleDto = domainScheduleService.getScheduleWithStudentLessons(createWeekScheduleDto(), studentLessons);

        assertEquals(weekScheduleDto, createWeekScheduleDto());
    }

    @Test
    public void testGetScheduleWithStudentLessons_ReplaceExistedInWeekScheduleDtoPositions() {
        DomainScheduleService domainScheduleService = new DomainScheduleService();

        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.JULY, 9, 14, 30, 0);

        StudentLesson newLesson = new StudentLesson(
                1L,
                new Student(1L, "email", "123"),
                new Lesson(1L, FRIDAY, localDateTime, "111", "Vladimirov", "student_link",
                        LessonType.LECTURE, LessonParity.ODD, new Subject(1L, "Subject"), new Group(1L, "20209")),
                true
        );

        WeekScheduleDto weekScheduleDto = domainScheduleService.getScheduleWithStudentLessons(createWeekScheduleDto(), listOf(newLesson));

        compareLessons(createWeekScheduleDto(), weekScheduleDto, newLesson);
    }

    @Test
    public void testGetScheduleWithStudentLessons_PlaceToEmptyInWeekScheduleDtoPositions() {
        DomainScheduleService domainScheduleService = new DomainScheduleService();

        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.JULY, 9, 10, 0, 0);

        StudentLesson newLesson = new StudentLesson(
                1L,
                new Student(1L, "email", "123"),
                new Lesson(1L, TUESDAY, localDateTime, "111", "Vladimirov", "student_link",
                        LessonType.LECTURE, LessonParity.ODD, new Subject(1L, "Subject"), new Group(1L, "20209")),
                true
        );

        WeekScheduleDto weekScheduleDto = domainScheduleService.getScheduleWithStudentLessons(createWeekScheduleDto(), listOf(newLesson));

        compareLessons(createWeekScheduleDto(), weekScheduleDto, newLesson);
    }

    public static WeekScheduleDto createWeekScheduleDto() {
        return new WeekScheduleDto(
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject1", LessonType.LECTURE, "09:00", "Ivanov",
                                new LessonPlace("1111", "meetLink"), LessonParity.EVEN),
                        new LessonScheduleDto(null, "Subject3", LessonType.LECTURE, "09:00", "Sidorov",
                                new LessonPlace("3333", "meetLink"), LessonParity.ALWAYS))
                ),
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject1", LessonType.SEMINAR, "09:00", "Ivanov",
                                new LessonPlace("1111", null), LessonParity.ODD),
                        new LessonScheduleDto(null, "Subject3", LessonType.SEMINAR, "10:50", "Sidorov",
                                new LessonPlace("3333", null), LessonParity.EVEN))
                ),
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject1", LessonType.LABORATORY, "12:40", "Ivanov",
                                new LessonPlace("1111", null), LessonParity.ALWAYS),
                        new LessonScheduleDto(null, "Subject3", LessonType.LABORATORY, "18:10", "Sidorov",
                                new LessonPlace("3333", null), LessonParity.ALWAYS))
                ),
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject2", LessonType.LECTURE, "12:40", "Petrov",
                                new LessonPlace("2222", "meetLink"), LessonParity.EVEN),
                        new LessonScheduleDto(null, "Subject4", LessonType.LECTURE, "20:00", "Alexandrov",
                                new LessonPlace("4444", "meetLink"), LessonParity.EVEN))
                ),
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject2", LessonType.SEMINAR, "14:30", "Petrov",
                                new LessonPlace("2222", null), LessonParity.ODD),
                        new LessonScheduleDto(null, "Subject4", LessonType.SEMINAR, "14:30", "Alexandrov",
                                new LessonPlace("4444", null), LessonParity.ODD))
                ),
                new DayScheduleDto(listOf(
                        new LessonScheduleDto(null, "Subject2", LessonType.LABORATORY, "14:30", "Petrov",
                                new LessonPlace("2222", null), LessonParity.ALWAYS),
                        new LessonScheduleDto(null, "Subject4", LessonType.LABORATORY, "16:20", "Alexandrov",
                                new LessonPlace("4444", null), LessonParity.ALWAYS))
                )
        );
    }

    public List<LessonScheduleDto> getDayLessons(DayName dayName, WeekScheduleDto weekScheduleDto) {
        return switch (dayName) {
            case MONDAY -> weekScheduleDto.monday().lessons();
            case TUESDAY -> weekScheduleDto.tuesday().lessons();
            case WEDNESDAY -> weekScheduleDto.wednesday().lessons();
            case THURSDAY ->  weekScheduleDto.thursday().lessons();
            case FRIDAY -> weekScheduleDto.friday().lessons();
            case SATURDAY -> weekScheduleDto.saturday().lessons();
        };
    }

    public void compareDays(DayName dayName, DayName compareDayName, DayScheduleDto dayScheduleDto, DayScheduleDto dayScheduleDtoResult) {
        if (dayName != compareDayName) {
            assertEquals(dayScheduleDto.lessons(), dayScheduleDtoResult.lessons());
        }
    }

    public void compareLessons(WeekScheduleDto weekScheduleDto, WeekScheduleDto weekScheduleDtoResult,
                            StudentLesson studentLesson) {
        DayName dayName = studentLesson.getLesson().getDayName();
        LocalDateTime localDateTime = studentLesson.getLesson().getStartTime();

        compareDays(dayName, MONDAY, weekScheduleDto.monday(), weekScheduleDtoResult.monday());
        compareDays(dayName, TUESDAY, weekScheduleDto.tuesday(), weekScheduleDtoResult.tuesday());
        compareDays(dayName, WEDNESDAY, weekScheduleDto.wednesday(), weekScheduleDtoResult.wednesday());
        compareDays(dayName, THURSDAY, weekScheduleDto.thursday(), weekScheduleDtoResult.thursday());
        compareDays(dayName, FRIDAY, weekScheduleDto.friday(), weekScheduleDtoResult.friday());
        compareDays(dayName, SATURDAY, weekScheduleDto.saturday(), weekScheduleDtoResult.saturday());

        List<LessonScheduleDto> lessonScheduleDtos = getDayLessons(dayName, weekScheduleDto);
        List<LessonScheduleDto> lessonScheduleResultDtos = getDayLessons(dayName, weekScheduleDtoResult);

        Lesson lesson = studentLesson.getLesson();

        for (LessonScheduleDto lessonScheduleResulDto : lessonScheduleResultDtos) {
            LocalDateTime lessonStartDateTimeResult = localDateTime.toLocalDate().atTime(LocalTime.parse(lessonScheduleResulDto.startTime()));
            for (LessonScheduleDto lessonScheduleDto : lessonScheduleDtos) {
                LocalDateTime lessonStartDateTime = localDateTime.toLocalDate().atTime(LocalTime.parse(lessonScheduleDto.startTime()));
                if (localDateTime.equals(lessonStartDateTimeResult) && localDateTime.equals(lessonStartDateTime)) {
                    assertNotEquals(lessonScheduleDto, lessonScheduleResulDto);
                    assertEquals(lessonScheduleResulDto.subject(), lesson.getSubject().getName());
                    assertEquals(lessonScheduleResulDto.type(), lesson.getLessonType());
                    assertEquals(lessonScheduleResulDto.teacher(), lesson.getTeacher());
                    assertEquals(lessonScheduleResulDto.place().room(), lesson.getRoom());
                    assertEquals(lessonScheduleResulDto.place().meetLink(), lesson.getMeetLink());
                    assertEquals(lessonScheduleResulDto.parity(), lesson.getLessonParity());
                } else if (lessonStartDateTimeResult.equals(lessonStartDateTime)){
                    assertEquals(lessonScheduleDto, lessonScheduleResulDto);
                }
            }
        }
    }
}
