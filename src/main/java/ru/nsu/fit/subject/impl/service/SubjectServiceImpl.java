package ru.nsu.fit.subject.impl.service;

import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.domain.model.entities.Subject;
import ru.nsu.fit.subject.impl.domain.model.repositories.SubjectRepository;

public class SubjectServiceImpl implements SubjectService{
    private SubjectRepository subjectRepository;

    @Override
    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }
    
}
