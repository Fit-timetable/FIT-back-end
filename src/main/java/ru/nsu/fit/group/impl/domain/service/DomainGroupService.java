package ru.nsu.fit.group.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.group.api.GroupDto;
import ru.nsu.fit.group.impl.domain.model.Group;

@Service
public class DomainGroupService {
    public GroupDto toGroupDto(Group group) {
        return new GroupDto(group.getId(), group.getNumber());
    }
}
