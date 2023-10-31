package ru.nsu.fit.schedule.impl.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.schedule.impl.data.entities.GlobalModeratorEntity;
import ru.nsu.fit.schedule.impl.data.entities.keys.GlobalModeratorEntityKey;

@Repository
public interface GlobalModeratorRepository extends JpaRepository<GlobalModeratorEntity, GlobalModeratorEntityKey> {
    GlobalModeratorEntity findGlobalModeratorByGlobalModeratorEntityKey_StudentEntityId(Long studentId);
}