package ru.nsu.fit.homework.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;

@Repository
public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, Long> {
    HomeworkFile findHomeworkFileByHomeworkId(Long id);
}
