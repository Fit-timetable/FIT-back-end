package ru.nsu.fit.group.impl.data.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.fit.group.impl.domain.model.Group;

public class GroupSpecifications {
    public static Specification<Group> groupNumberStartsWith(String number) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("number"), number + "%");
    }
}