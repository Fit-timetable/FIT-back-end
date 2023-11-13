package ru.nsu.fit.resource.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.resource.impl.domain.model.ResourceDriveMaterial;

@Repository
public interface ResourceDriveMaterialRepository extends JpaRepository<ResourceDriveMaterial, Long> {
    ResourceDriveMaterial findByResourceId(Long id);
}
