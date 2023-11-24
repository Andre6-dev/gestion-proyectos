package com.hiper.agq.entity.shared;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * andre on 23/11/2023
 * Entidad abstracta que contiene los atributos comunes de las entidades
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "creado_en", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "modificado_en")
    private Instant lastModifiedAt;
}
