package ru.nsu.fit.resource.api.dto;

import ru.nsu.fit.resource.impl.domain.model.ResourceStatus;

public record ResourceResponseDto(
        Long id,
        String name,
        String description,
        ResourceStatus status,
        String expirationDate,
        String files
) {
}
