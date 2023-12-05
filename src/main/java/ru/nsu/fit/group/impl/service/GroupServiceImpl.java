package ru.nsu.fit.group.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.nsu.fit.group.api.GroupDto;
import ru.nsu.fit.group.api.GroupService;
import ru.nsu.fit.group.impl.data.GroupRepository;
import ru.nsu.fit.group.impl.data.specification.GroupSpecifications;
import ru.nsu.fit.group.impl.data.GroupStudentRepository;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.group.impl.domain.service.DomainGroupService;
import ru.nsu.fit.group.impl.domain.service.GroupParser;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupStudentRepository groupStudentRepository;
    private final GroupRepository groupRepository;
    private final DomainGroupService domainGroupService;

    @Override
    public Group getGroupByStudentId(Long studentId) {
        var groupStudent = groupStudentRepository.findByStudentId(studentId).orElseThrow();
        return groupStudent.getGroup();
    }

    @Override
    public List<GroupDto> getGroupsByStartGroupNumber(String number) {
        return GroupParser.parse(number);
    }
}
