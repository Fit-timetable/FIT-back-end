package ru.nsu.fit.resource.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.data.ResourceDriveMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceLinkMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceRepository;
import ru.nsu.fit.resource.impl.domain.model.Resource;
import ru.nsu.fit.resource.impl.domain.model.ResourceDriveMaterial;
import ru.nsu.fit.resource.impl.domain.model.ResourceLinkMaterial;
import ru.nsu.fit.resource.impl.domain.service.ResourceParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResourceService {
    private ResourceRepository resourceRepository;
    private ResourceDriveMaterialRepository resourceDriveMaterialRepository;
    private ResourceLinkMaterialRepository resourceLinkMaterialRepository;
    private ResourceParser resourceParser;

    public List<Resource> getResourcesBySubjectId(Long id) {
        return resourceRepository.findAllBySubjectId(id);
    }

    public List<String> getResourcesMaterial(List<Resource> resources) {
        List<Long> materialsId = resources.stream().map(Resource::getId).toList();
        List<String> materials = new ArrayList<>();
        for (Long id : materialsId) {
            Optional<ResourceLinkMaterial> link = resourceLinkMaterialRepository.findByResourceId(id);
            Optional<ResourceDriveMaterial> drive = resourceDriveMaterialRepository.findByResourceId(id);
            if (link.isPresent()) {
                materials.add(link.get().getLink());
            } else if (drive.isPresent()) {
                materials.add(drive.get().getFileDriveUri());
            } else {
                materials.add(null);
            }
        }
        return materials;
    }

    public List<ResourceResponseDto> getResourcesDtoBySubjectId(Long id) {
        List<Resource> resources = getResourcesBySubjectId(id);
        if (resources != null) {
            List<String> resourcesMaterial = getResourcesMaterial(resources);
            return resourceParser.toResourceResponseDtoList(resources, resourcesMaterial);
        }
        return null;
    }
}
