package ru.nsu.fit.resource.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.domain.model.Resource;

@Component
@Getter
@AllArgsConstructor
public class ResourceDomainService {
    public static ResourceResponseDto toResourceResponseDtoList(Resource resources, String materials){
        return new ResourceResponseDto(resources.getId(),
                    resources.getName(),
                    resources.getDescription(),
                    resources.getStatus(),
                    resources.getDeletionDate(),
                    materials);
    }
}
