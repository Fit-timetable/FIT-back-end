package ru.nsu.fit.resource.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.nsu.fit.resource.api.ResouceService;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.data.ResourceDriveMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceLinkMaterialRepository;
import ru.nsu.fit.resource.impl.data.ResourceRepository;
import ru.nsu.fit.resource.impl.domain.model.Resource;
import ru.nsu.fit.resource.impl.domain.service.ResourceDomainService;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResourceServiceImpl implements ResouceService {
    private ResourceRepository resourceRepository;
    private ResourceDriveMaterialRepository resourceDriveMaterialRepository;
    private ResourceLinkMaterialRepository resourceLinkMaterialRepository;
    private ResourceDomainService resourceParser;

    private List<Resource> getResourcesBySubjectId(Long id) {
        return resourceRepository.findAllBySubjectId(id);
    }

    private List<String> getResourcesMaterial(List<Resource> resources) {
        List<Long> materialsId = resources.stream().map(Resource::getId).toList();
        List<String> materials = new ArrayList<>();
        
        for (Long id : materialsId) {
            String newMaterial;

            if ((newMaterial = resourceLinkMaterialRepository.findByResourceId(id).getLink()) != null) {
                materials.add(newMaterial);
            } 
            
            else if ((newMaterial = resourceDriveMaterialRepository.findByResourceId(id).getFileDriveUri()) != null) {
                materials.add(newMaterial);
            } 
            
            else {
                materials.add(newMaterial);
            }
        }
        
        return materials;
    }

    @Override
    public List<ResourceResponseDto> getResourcesDtoBySubjectId(Long id) {
        List<Resource> resources = getResourcesBySubjectId(id);
        List<String> resourcesMaterial = getResourcesMaterial(resources);
        
        return resourceParser.toResourceResponseDtoList(resources, resourcesMaterial);
    }
}
