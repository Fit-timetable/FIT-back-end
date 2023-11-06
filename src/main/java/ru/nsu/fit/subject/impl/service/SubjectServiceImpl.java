package ru.nsu.fit.subject.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.data.SubjectRepository;
import ru.nsu.fit.subject.impl.domain.model.Subject;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

}
