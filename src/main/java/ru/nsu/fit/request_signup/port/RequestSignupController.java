package ru.nsu.fit.request_signup.port;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static ru.nsu.fit.request_signup.port.RequestSignupUrl.RequestSignup_URL;
import lombok.AllArgsConstructor;
import ru.nsu.fit.request_signup.api.RequestSignupService;

@RestController
@AllArgsConstructor
@RequestMapping(RequestSignup_URL)
public class RequestSignupController {
    private final RequestSignupService requestSignupService ;

    @GetMapping()
    public void sendConfirmMessage(@RequestBody String email) {
        requestSignupService.sendConfirmMessage(email);
    }
}
