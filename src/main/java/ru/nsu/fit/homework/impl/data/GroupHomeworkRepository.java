package ru.nsu.fit.homework.impl.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.homework.impl.domain.model.GroupHomework;

@Repository
public interface GroupHomeworkRepository extends JpaRepository<GroupHomework, Long> {
    Optional<GroupHomework> findByHomeworkId(Long id);
}
