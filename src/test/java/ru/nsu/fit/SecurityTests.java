package ru.nsu.fit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.fit.email.api.dto.EmailDTO;
import ru.nsu.fit.email.port.EmailUrl;
import ru.nsu.fit.schedule.api.dto.PinRequestDto;
import ru.nsu.fit.schedule.port.ScheduleUrl;
import ru.nsu.fit.security.api.Tokens;
import ru.nsu.fit.security.port.TokenUrl;
import ru.nsu.fit.signup.api.dto.ConfirmSignupDTO;
import ru.nsu.fit.signup.port.SignupUrl;
import ru.nsu.fit.utils.CaseTestWithSecurityEnabled;


@CaseTestWithSecurityEnabled
@Sql("classpath:db/insert-default-students.sql")
public class SecurityTests {

    @Autowired
    private WebTestClient webTestClient;

    private final static String authUsername = "i.ivanov222@g.nsu.ru";
    private final static String authPassword = "12345678";

    @Test
    public void Basic_authentication_with_valid_credentials_is_perfomed() {
        webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, authPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .jsonPath("$.accessTokenExpiry").isNotEmpty()
                .jsonPath("$.refreshToken").isNotEmpty()
                .jsonPath("$.refreshTokenExpiry").isNotEmpty();
    }

    @Test
    public void Basic_authentication_with_invalid_credentials_results_in_failure() {
        webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, "abcde"))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void Authorization_with_access_token_is_perfomed() {
        Tokens tokens = webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, authPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tokens.class)
                .returnResult().getResponseBody();
        assert tokens != null;

        webTestClient.method(HttpMethod.POST)
                .uri(ScheduleUrl.SCHEDULE_URL + ScheduleUrl.PIN_URL)
                .body(Mono.just(new PinRequestDto("23201")), PinRequestDto.class)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.accessToken()))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void Requesting_page_without_authorization_results_in_failure() {
        webTestClient.method(HttpMethod.GET)
                .uri(ScheduleUrl.PIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void Refresh_token() {
        Tokens tokens = webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, authPassword))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tokens.class)
                .returnResult().getResponseBody();
        assert tokens != null;

        webTestClient.method(HttpMethod.POST)
                .uri(TokenUrl.TOKEN_REFRESH)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.refreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .jsonPath("$.accessTokenExpiry").isNotEmpty()
                .jsonPath("$.refreshToken").isEmpty()
                .jsonPath("$.refreshTokenExpiry").isEmpty();
    }

    @Test
    public void Token_logout_leads_to_invalidated_refresh_token() {
        Tokens tokens = webTestClient.method(HttpMethod.GET)
                .uri(TokenUrl.TOKENS)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(authUsername, authPassword))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tokens.class)
                .returnResult().getResponseBody();
        assert tokens != null;

        webTestClient.method(HttpMethod.POST)
                .uri(TokenUrl.TOKEN_LOGOUT)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.refreshToken()))
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.method(HttpMethod.POST)
                .uri(TokenUrl.TOKEN_REFRESH)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.refreshToken()))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    // TODO: test if signup endpoints require authorization
    @Test
    @Sql("classpath:db/insert-default-confirmation_code_query.sql")
    public void could_create_new_student_without_authorities() {
        var requestDto = new ConfirmSignupDTO(
                "absract_user@mail.ru",
                "adminadmin",
                123456
        );

        webTestClient.method(HttpMethod.POST)
                .uri(SignupUrl.CONFIRM_SIGNUP)
                .body(Mono.just(requestDto),ConfirmSignupDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

//    @Test
//    public void could_send_message_without_authorities() {
//        var requestDto = new EmailDTO(
//                "absract_user@mail.ru"
//        );
//
//        webTestClient.method(HttpMethod.POST)
//                .uri(EmailUrl.REQUEST_SIGNUP)
//                .body(Mono.just(requestDto), EmailDTO.class)
//                .exchange()
//                .expectStatus().isOk();
//    }
}
