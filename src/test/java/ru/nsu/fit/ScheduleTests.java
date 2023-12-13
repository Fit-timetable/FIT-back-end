package ru.nsu.fit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.schedule.api.dto.LessonScheduleDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.port.ScheduleUrl;
import ru.nsu.fit.utils.CaseTest;

@CaseTest
public class ScheduleTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void Schedule_has_openings_only_before_or_between_lessons() {
        var group = "22201";

        var schedule = webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(ScheduleUrl.SCHEDULE_URL + ScheduleUrl.GROUP_URL)
                        .build(group))
                .exchange()
                .expectStatus().isOk()
                .expectBody(WeekScheduleDto.class)
                .returnResult()
                .getResponseBody();
        assert schedule != null;

        var mondayLessons = schedule.monday().lessons().toArray(new LessonScheduleDto[0]);
        Assertions.assertEquals(mondayLessons[0].type(), LessonType.OPENING);
        Assertions.assertEquals(mondayLessons[1].type(), LessonType.LECTURE);
        Assertions.assertEquals(mondayLessons[2].type(), LessonType.OPENING);
        Assertions.assertEquals(mondayLessons[3].type(), LessonType.SEMINAR);
        Assertions.assertEquals(mondayLessons.length, 4);
    }


}
