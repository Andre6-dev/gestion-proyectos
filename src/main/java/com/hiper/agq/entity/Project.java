package com.hiper.agq.entity;

import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.entity.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * andre on 23/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "proyectos")
public class Project extends AbstractEntity {

    @Column(name = "nombre_proyecto", nullable = false)
    private String name;

    @Column(name = "descripcion_proyecto", nullable = false)
    private String description;

    @Column(name = "tipo_proyecto", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeProject type;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime endDate;

    private String logo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) && Objects.equals(description, project.description) && type == project.type && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate) && Objects.equals(logo, project.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, type, startDate, endDate, logo);
    }
}
