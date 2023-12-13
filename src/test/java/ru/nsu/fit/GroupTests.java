package ru.nsu.fit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.nsu.fit.group.port.GroupUrl;
import ru.nsu.fit.utils.CaseTest;

import java.util.List;

@CaseTest
public class GroupTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Sql("classpath:db/insert-default-groups.sql")
    public void Group_list_is_properly_filtered() {
        var groups = webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(GroupUrl.GROUP)
                        .queryParam("name", "22")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();
        assert groups != null;

        Assertions.assertEquals(groups.size(), 20);
    }
}
