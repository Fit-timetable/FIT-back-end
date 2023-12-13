package ru.nsu.fit.resource.impl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.resource.impl.domain.model.ResourceLinkMaterial;

@Repository
public interface ResourceLinkMaterialRepository extends JpaRepository<ResourceLinkMaterial, Long> {
    ResourceLinkMaterial findByResourceId(Long id);
}
