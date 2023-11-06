package ru.nsu.fit.group.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.group.api.GroupService;
import ru.nsu.fit.group.impl.data.GroupStudentRepository;
import ru.nsu.fit.group.impl.domain.model.Group;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupStudentRepository groupStudentRepository;

    @Override
    public Group getGroupByStudentId(Long studentId) {
        var groupStudent = groupStudentRepository.findByStudentId(studentId).orElseThrow();
        return groupStudent.getGroup();
    }

}
