package ru.nsu.fit.resource.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.domain.model.Resource;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@AllArgsConstructor
public class ResourceParser {
    public List<ResourceResponseDto> toResourceResponseDtoList(List<Resource> resources,List<String> materials){
        List<ResourceResponseDto> resourceResponseDtoList = new ArrayList<>(resources.size());
        int index = 0;
        for (Resource resource: resources){
            resourceResponseDtoList.add(new ResourceResponseDto(resource.getId(),
                    resource.getName(),
                    resource.getDescription(),
                    resource.getStatus(),
                    resource.getDeletionDate().toString(),
                    materials.get(index)));
            index++;
        }
        return resourceResponseDtoList;
    }
}
