package ru.nsu.fit.email.port;

import static ru.nsu.fit.email.port.EmailUrl.REQUEST_SIGNUP;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.RequestSignupService;
import ru.nsu.fit.email.api.dto.EmailDTO;

@RestController
@AllArgsConstructor
@Tag(name="Отправка писем")
@RequestMapping(REQUEST_SIGNUP)
public class EmailController {
    private final RequestSignupService requestSignupService ;

    @Operation(summary = "Отправка подтверждающего письма")
    @PostMapping()
    public void sendConfirmMessage(@RequestBody EmailDTO email) {
        requestSignupService.sendConfirmMessage(email.email());
    }
}
