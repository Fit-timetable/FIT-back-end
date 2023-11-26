package ru.nsu.fit.group.api;

import ru.nsu.fit.group.impl.domain.model.Group;

import java.util.List;

public interface GroupService {
    Group getGroupByStudentId(Long studentId);
    List<GroupDto> getGroupsByStartGroupNumber(String number);
}
