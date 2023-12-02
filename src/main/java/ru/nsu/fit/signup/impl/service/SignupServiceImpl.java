package ru.nsu.fit.signup.impl.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.signup.api.SignupService;
import ru.nsu.fit.signup.api.dto.ConfirmSignupDTO;
import ru.nsu.fit.signup.impl.data.ConfirmCodeQueryRepository;
import ru.nsu.fit.signup.impl.domain.model.entities.ConfirmCodeQuery;
import ru.nsu.fit.signup.impl.domain.service.SignupDomainService;
import ru.nsu.fit.student.api.StudentService;

@Service
@AllArgsConstructor
public class SignupServiceImpl implements SignupService {
    private final StudentService studentService;
    private final ConfirmCodeQueryRepository confirmCodeQueryRepository;

    @Override
    public void createStudent(ConfirmSignupDTO confirmSignupDTO) {
        String confirmCodeFromDTO = Integer.toString(confirmSignupDTO.confirmationCode());

        ConfirmCodeQuery confirmCodeQuery = confirmCodeQueryRepository.findByEmail(confirmSignupDTO.email())
        .orElseThrow(() -> new NoSuchElementException("No code found for the given email"));

        String confirmCodeFromQuery = confirmCodeQuery.getCode();

        if(confirmCodeFromQuery.equals(confirmCodeFromDTO)){
            studentService.saveStudent(confirmSignupDTO.email(), confirmSignupDTO.password());
        }

        else{
            throw new IllegalArgumentException("Invalid confirmation code");
        }
    }

    @Override
    public void pushConfirmCodeToQuery(String email, String code, LocalDateTime expirationDate) {
        confirmCodeQueryRepository.save(SignupDomainService.createConfirmCodeQuery(email, code, expirationDate));
    }  
}
