package ru.nsu.fit.homework.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.homework.impl.domain.model.GroupHomework;

@Repository
public interface GroupHomeworkRepository extends JpaRepository<GroupHomework, Long> {
    GroupHomework findByHomeworkId(Long id);
}
