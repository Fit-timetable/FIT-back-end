package ru.nsu.fit.resource.impl.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resource_link_material")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResourceLinkMaterial {
    @Id
    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "link")
    private String link;

}
