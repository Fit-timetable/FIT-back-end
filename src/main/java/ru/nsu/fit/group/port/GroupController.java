package ru.nsu.fit.group.port;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.group.api.GroupDto;
import ru.nsu.fit.group.api.GroupService;

import java.util.List;

@AllArgsConstructor
@Tag(name="Группа")
@RestController
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Получить список доступных групп")
    @GetMapping(GroupUrl.GROUP)
    public List<GroupDto> getGroupsByStartGroupNumber(@RequestParam(value = "name", required = false) String name) {
        return groupService.getGroupsByStartGroupNumber(name);
    }
}
