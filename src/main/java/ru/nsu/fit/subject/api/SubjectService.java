package ru.nsu.fit.subject.api;

import ru.nsu.fit.subject.impl.domain.model.entities.Subject;

public interface SubjectService{
    Subject getSubject(Long id);
}
