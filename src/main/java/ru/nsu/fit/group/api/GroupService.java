package ru.nsu.fit.group.api;

import ru.nsu.fit.group.impl.domain.model.Group;

public interface GroupService {
    Group getGroupByStudentId(Long studentId);
}
