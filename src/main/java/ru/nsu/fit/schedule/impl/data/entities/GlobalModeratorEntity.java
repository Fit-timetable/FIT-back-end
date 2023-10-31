package ru.nsu.fit.schedule.impl.data.entities;

import lombok.*;
import jakarta.persistence.*;
import ru.nsu.fit.schedule.impl.data.entities.keys.GlobalModeratorEntityKey;

@Entity
@Table(name = "global_moderator")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalModeratorEntity {
    @EmbeddedId
    private GlobalModeratorEntityKey globalModeratorEntityKey;
}
