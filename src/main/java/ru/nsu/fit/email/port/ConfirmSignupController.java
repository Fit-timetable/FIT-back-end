package ru.nsu.fit.email.port;

import static ru.nsu.fit.email.port.ConfirmSignupUrl.CONFIRM_SIGNUP;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.ConfirmSignupService;
import ru.nsu.fit.email.api.dto.ConfirmSignupDTO;

@RestController
@AllArgsConstructor
@Tag(name="Подтверждение регистрации")
@RequestMapping(CONFIRM_SIGNUP)
public class ConfirmSignupController {
    private final ConfirmSignupService confirmSignupService;

    @Operation(summary = "Проверка введенного кода")
    @PostMapping()
    public void sendConfirmMessage(@RequestBody ConfirmSignupDTO confirmSignDTO) {
        confirmSignupService.createStudent(confirmSignDTO);
    }
}
