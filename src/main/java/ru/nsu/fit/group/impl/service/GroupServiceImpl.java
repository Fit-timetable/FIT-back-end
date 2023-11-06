package ru.nsu.fit.group.impl.service;

import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.group.api.GroupService;
import ru.nsu.fit.group.impl.domain.model.repositories.GroupStudentRepository;

public class GroupServiceImpl implements GroupService{
    private GroupStudentRepository groupStudentRepository;

    @Override
    public Group getGroupByStudentId(Long studentId) {
        return groupStudentRepository.findByStudentId(studentId).get().getGroup();
    }
    
}
