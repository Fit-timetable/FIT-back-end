package ru.nsu.fit.resource.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.resource.impl.domain.model.ResourceDriveMaterial;

import java.util.Optional;

@Repository
public interface ResourceDriveMaterialRepository extends JpaRepository<ResourceDriveMaterial, Long> {
    Optional<ResourceDriveMaterial> findByResourceId(Long id);
}
