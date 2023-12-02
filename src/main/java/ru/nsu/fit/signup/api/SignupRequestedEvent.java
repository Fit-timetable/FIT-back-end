package ru.nsu.fit.signup.api;

import org.springframework.context.ApplicationEvent;

public class SignupRequestedEvent extends ApplicationEvent {
    private final String email;
    private final String confirmationCode;

    public String getEmail(){
        return email;
    }

    public String getConfirmationCode(){
        return confirmationCode;
    }
    
    public SignupRequestedEvent(Object source, String email, String confirmationCode) {
        super(source);
        this.email = email;
        this.confirmationCode = confirmationCode;
    }
}