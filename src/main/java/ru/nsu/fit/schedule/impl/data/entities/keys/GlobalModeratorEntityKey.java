package ru.nsu.fit.schedule.impl.data.entities.keys;

import lombok.*;
import jakarta.persistence.*;
import ru.nsu.fit.schedule.impl.data.entities.StudentEntity;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GlobalModeratorEntityKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity studentEntity;
}