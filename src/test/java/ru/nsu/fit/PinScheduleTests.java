package ru.nsu.fit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.fit.schedule.api.dto.PinRequestDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.port.ScheduleUrl;
import ru.nsu.fit.security.api.Tokens;
import ru.nsu.fit.security.port.TokenUrl;
import ru.nsu.fit.utils.CaseTestWithSecurityEnabled;

@CaseTestWithSecurityEnabled
public class PinScheduleTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Sql("classpath:db/insert-default-students.sql")
    public void Schedule_could_be_pinned() {
        // Arrange
        var requestDto = new PinRequestDto("22201");

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

        WeekScheduleDto groupWeekSchedule = webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(ScheduleUrl.SCHEDULE_URL + ScheduleUrl.GROUP_URL)
                        .build(requestDto.group()))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(WeekScheduleDto.class)
                .returnResult()
                .getResponseBody();
        assert groupWeekSchedule != null;

        // Act
        webTestClient.method(HttpMethod.POST)
                .uri(ScheduleUrl.SCHEDULE_URL + ScheduleUrl.PIN_URL)
                .body(Mono.just(requestDto), PinRequestDto.class)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .exchange()
                .expectStatus().isOk();

        // Assert
        webTestClient.method(HttpMethod.GET)
                .uri(ScheduleUrl.SCHEDULE_URL + ScheduleUrl.PIN_URL)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .exchange()
                .expectBody(WeekScheduleDto.class)
                .isEqualTo(groupWeekSchedule);
    }
}
