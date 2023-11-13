package ru.nsu.fit.resource.api;

import java.util.List;

import ru.nsu.fit.resource.api.dto.ResourceResponseDto;

public interface ResouceService {
    List<ResourceResponseDto> getResourcesDtoBySubjectId(Long id);
}
