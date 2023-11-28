package ru.nsu.fit.signup.port;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ru.nsu.fit.signup.api.ConfirmSignupService;
import ru.nsu.fit.signup.api.SignupRequestedEvent;

@Component
public class SignupRequestedEventListener implements ApplicationListener<SignupRequestedEvent> {
    private final ConfirmSignupService confirmSignupService;

    public SignupRequestedEventListener(ConfirmSignupService confirmSignupService) {
        this.confirmSignupService = confirmSignupService;
    }

    @Override
    public void onApplicationEvent(SignupRequestedEvent event) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusMinutes(5);
        confirmSignupService.pushConfirmCodeToQuery(event.getEmail(), event.getConfirmationCode(), futureTime);
    }
}

