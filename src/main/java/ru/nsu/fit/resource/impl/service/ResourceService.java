package ru.nsu.fit.resource.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.data.ResourceDriveMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceLinkMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceRepository;
import ru.nsu.fit.resource.impl.domain.model.Resource;
import ru.nsu.fit.resource.impl.domain.service.ResourceParser;

import java.util.ArrayList;
import java.util.List;

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
            String newMaterial;
            if ((newMaterial = resourceLinkMaterialRepository.findByResourceId(id).getLink()) != null) {
                materials.add(newMaterial);
            } else if ((newMaterial = resourceDriveMaterialRepository.findByResourceId(id).getFileDriveUri()) != null) {
                materials.add(newMaterial);
            } else {
                materials.add(newMaterial);
            }
        }
        return materials;
    }

    public List<ResourceResponseDto> getResourcesDtoBySubjectId(Long id) {
        List<Resource> resources = getResourcesBySubjectId(id);
        List<String> resourcesMaterial = getResourcesMaterial(resources);
        return resourceParser.toResourceResponseDtoList(resources, resourcesMaterial);
    }
}
