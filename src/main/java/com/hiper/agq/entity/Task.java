package com.hiper.agq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiper.agq.entity.enums.PriorityEnum;
import com.hiper.agq.entity.enums.TaskStatus;
import com.hiper.agq.entity.enums.TypeTask;
import com.hiper.agq.entity.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * andre on 23/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "tareas")
public class Task extends AbstractEntity {

    @Column(name = "nombre_tarea", nullable = false)
    private String name;

    @Column(name = "descripcion_tarea")
    private String description;

    @Column(name = "estado_tarea", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "prioridad_tarea", nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @Column(name = "tipo_tarea", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeTask type;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "tiempo_estimado", nullable = false)
    private String estimatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Project project;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tareas_usuarios",
            joinColumns = @JoinColumn(
                    name = "tarea_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "usuario_id",
                    referencedColumnName = "id"
            )
    )
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && priority == task.priority && type == task.type && Objects.equals(startDate, task.startDate) && Objects.equals(endDate, task.endDate) && Objects.equals(estimatedTime, task.estimatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, priority, type, startDate, endDate, estimatedTime);
    }

    public void assignUserToTask(User user) {
        this.users.add(user);
        user.getTasks().add(this);
    }

    public void removeUserFromTask(User user) {
        this.users.remove(user);
        user.getTasks().remove(this);
    }
}
