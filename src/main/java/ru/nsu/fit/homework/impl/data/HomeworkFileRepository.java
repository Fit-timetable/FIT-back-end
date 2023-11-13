package ru.nsu.fit.homework.impl.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;

@Repository
public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, Long> {
    Optional<HomeworkFile> findHomeworkFileByHomeworkId(Long id);
}
