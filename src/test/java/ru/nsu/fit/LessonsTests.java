package ru.nsu.fit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.lesson.api.LessonDate;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.dto.CancelLessonDto;
import ru.nsu.fit.lesson.api.dto.EditLessonDto;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.api.dto.LessonIdDto;
import ru.nsu.fit.lesson.impl.data.LessonRepository;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.port.LessonUrl;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.security.api.Tokens;
import ru.nsu.fit.security.port.TokenUrl;
import ru.nsu.fit.utils.CaseTestWithSecurityEnabled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@CaseTestWithSecurityEnabled
public class LessonsTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LessonRepository lessonRepository;

    private Tokens getAuthentificateTokens() {
        String authUsername = "i.ivanov222@g.nsu.ru";
        String authPassword = "12345678";
        Tokens tokens = webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, authPassword))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tokens.class)
                .returnResult().getResponseBody();
        assert tokens != null;
        return tokens;
    }

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    public void Lesson_could_be_created() {

        Tokens tokens = getAuthentificateTokens();

        var requestDto = new LessonForm(
                "Физкультура и спорт",
                LessonType.PRACTICE,
                new LessonDate(DayName.MONDAY, LocalDateTime.of(LocalDate.now(), LocalTime.parse("09:00"))),
                new LessonPlace("3107", "meetLink"),
                LessonParity.ALWAYS
        );

        var lessonIdDto = new LessonIdDto(1L);

        webTestClient.method(HttpMethod.POST)
                .uri(LessonUrl.LESSON_URL)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .body(Mono.just(requestDto), LessonForm.class)
                .exchange()
                .expectBody(LessonIdDto.class)
                .isEqualTo(lessonIdDto);

        Lesson lesson = lessonRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(lesson);
        Assertions.assertEquals(1L, lesson.getId());
        Assertions.assertEquals("meetLink", lesson.getMeetLink());
        Assertions.assertEquals("3107", lesson.getRoom());
        Assertions.assertNull(lesson.getTeacher());
        Assertions.assertEquals(DayName.MONDAY, lesson.getDayName());
        Assertions.assertNull(lesson.getGroup());
        Assertions.assertEquals(LessonParity.ALWAYS, lesson.getLessonParity());
        Assertions.assertEquals(LessonType.PRACTICE, lesson.getLessonType());
        Assertions.assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.parse("09:00")), lesson.getStartTime());
        Assertions.assertEquals("Физкультура и спорт", lesson.getSubject().getName());

    }

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    @Sql("classpath:db/insert-default-groups.sql")
    @Sql("classpath:db/insert-default-lesson.sql")
    public void Lesson_could_be_changed_correctly() throws ParseException {
        Tokens tokens = getAuthentificateTokens();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2022-01-12");
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        var requestDto = new EditLessonDto(
                "Физкультура и спорт",
                new LessonDate(DayName.FRIDAY, LocalDateTime.of(localDate, LocalTime.parse("14:30"))),
                "Petrov R. R.",
                 new LessonPlace("3107", "meetLink")
        );

        webTestClient.method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.path(LessonUrl.LESSON_URL + LessonUrl.LESSON_ID_URL)
                        .build(1L))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .body(Mono.just(requestDto), EditLessonDto.class)
                .exchange()
                .expectStatus().isOk();

        Lesson lesson = lessonRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(lesson);
        Assertions.assertEquals(1L, lesson.getId());
        Assertions.assertEquals("meetLink", lesson.getMeetLink());
        Assertions.assertEquals("3107", lesson.getRoom());
        Assertions.assertEquals("Petrov R. R.", lesson.getTeacher());
        Assertions.assertEquals(DayName.FRIDAY, lesson.getDayName());
        Assertions.assertNotNull(lesson.getGroup());
        Assertions.assertEquals(LessonParity.EVEN, lesson.getLessonParity());
        Assertions.assertEquals(LessonType.LECTURE, lesson.getLessonType());
        Assertions.assertEquals(LocalDateTime.of(localDate, LocalTime.parse("14:30")), lesson.getStartTime());
        Assertions.assertEquals("Физкультура и спорт", lesson.getSubject().getName());
    }

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    @Sql("classpath:db/insert-default-groups.sql")
    @Sql("classpath:db/insert-default-lesson.sql")
    public void Lesson_could_be_deleted() {
        Tokens tokens = getAuthentificateTokens();

        webTestClient.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.path(LessonUrl.LESSON_URL + LessonUrl.LESSON_ID_URL)
                        .build(1L))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .exchange()
                .expectStatus().isOk();
        
        webTestClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder.path(LessonUrl.LESSON_URL + LessonUrl.ID_URL)
                    .build(1L)) 
            .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    @Sql("classpath:db/insert-default-groups.sql")
    @Sql("classpath:db/insert-default-lesson.sql")
    public void Lesson_in_chosen_day_could_be_deleted() throws ParseException {
        Tokens tokens = getAuthentificateTokens();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2022-01-12");

        var requestDto = new CancelLessonDto(date);

        webTestClient.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.path(LessonUrl.LESSON_URL + LessonUrl.LESSON_CANCEL_URL)
                        .build(1L))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .body(Mono.just(requestDto), CancelLessonDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    @Sql("classpath:db/insert-default-groups.sql")
    @Sql("classpath:db/insert-default-lesson.sql")
    public void Lesson_could_be_get() {
        Tokens tokens = getAuthentificateTokens();

        LessonDetailsDto lessonDetailsDto = webTestClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder.path(LessonUrl.LESSON_URL + LessonUrl.ID_URL)
                    .build(1L)) 
            .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
            .exchange()
            .expectBody(LessonDetailsDto.class)
            .returnResult().getResponseBody();

        
        Assertions.assertNotNull(lessonDetailsDto);
        Assertions.assertEquals("14:30", lessonDetailsDto.startTime());
        Assertions.assertEquals("Физкультура и спорт", lessonDetailsDto.subject());
        Assertions.assertEquals("Robert Johnson", lessonDetailsDto.teacher());
        Assertions.assertEquals("Room 103", lessonDetailsDto.place().room());
        Assertions.assertEquals("meetlink3", lessonDetailsDto.place().meetLink());
        Assertions.assertNull(lessonDetailsDto.resource());
        Assertions.assertNull(lessonDetailsDto.upcomingHomework());
    }
}
