package ru.nsu.fit.email.impl.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.fit.email.api.ConfirmSignupService;
import ru.nsu.fit.email.api.dto.ConfirmSignupDTO;
import ru.nsu.fit.email.impl.data.ConfirmCodeQueryRepository;
import ru.nsu.fit.email.impl.domain.model.entities.ConfirmCodeQuery;
import ru.nsu.fit.student.impl.data.StudentRepository;
import ru.nsu.fit.student.impl.domain.model.Student;

@Service
@AllArgsConstructor
public class ConfirmSignupServiceImpl implements ConfirmSignupService {
    private final StudentRepository studentRepository;
    private final ConfirmCodeQueryRepository confirmCodeQueryRepository;

    @Override
    public void createStudent(ConfirmSignupDTO confirmSignupDTO) {
        String confirmCodeFromDTO = Integer.toString(confirmSignupDTO.confirmationCode());

        ConfirmCodeQuery confirmCodeQuery = confirmCodeQueryRepository.findByEmail(confirmSignupDTO.email());

        if(confirmCodeQuery == null){
            return;
        }

        String confirmCodeFromQuery = confirmCodeQuery.getCode();

        if(confirmCodeFromQuery.equals(confirmCodeFromDTO)){
            Student student = new Student(confirmSignupDTO.email(), confirmSignupDTO.password());
            studentRepository.save(student);
        }
    }
}
