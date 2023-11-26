package ru.nsu.fit.group.impl.data;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.group.impl.domain.model.Group;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("NullableProblems")
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    List<Group> findAll(Specification<Group> specification);
    Optional<Group> findByNumber(String number);
}