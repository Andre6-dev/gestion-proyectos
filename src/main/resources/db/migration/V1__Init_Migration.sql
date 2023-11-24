CREATE TABLE proyectos
(
    id                   UUID                        NOT NULL,
    creado_en            TIMESTAMP WITHOUT TIME ZONE,
    modificado_en        TIMESTAMP WITHOUT TIME ZONE,
    nombre_proyecto      VARCHAR(255)                NOT NULL,
    descripcion_proyecto VARCHAR(255)                NOT NULL,
    tipo_proyecto        VARCHAR(255)                NOT NULL,
    fecha_inicio         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fecha_fin            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    logo                 VARCHAR(255),
    CONSTRAINT pk_proyectos PRIMARY KEY (id)
);

CREATE TABLE tareas
(
    id                UUID                        NOT NULL,
    creado_en         TIMESTAMP WITHOUT TIME ZONE,
    modificado_en     TIMESTAMP WITHOUT TIME ZONE,
    nombre_tarea      VARCHAR(255)                NOT NULL,
    descripcion_tarea VARCHAR(255)                NOT NULL,
    estado_tarea      VARCHAR(255)                NOT NULL,
    prioridad_tarea   VARCHAR(255)                NOT NULL,
    tipo_tarea        VARCHAR(255)                NOT NULL,
    fecha_inicio      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fecha_fin         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    tiempo_estimado   VARCHAR(255)                NOT NULL,
    proyecto_id       UUID                        NOT NULL,
    CONSTRAINT pk_tareas PRIMARY KEY (id)
);

CREATE TABLE tareas_usuarios
(
    tarea_id   UUID NOT NULL,
    usuario_id UUID NOT NULL,
    CONSTRAINT pk_tareas_usuarios PRIMARY KEY (tarea_id, usuario_id)
);

CREATE TABLE usuarios
(
    id             UUID         NOT NULL,
    creado_en      TIMESTAMP WITHOUT TIME ZONE,
    modificado_en  TIMESTAMP WITHOUT TIME ZONE,
    nombre_usuario VARCHAR(255) NOT NULL,
    correo         VARCHAR(255) NOT NULL,
    tipo_usuario   VARCHAR(255) NOT NULL,
    telefono       VARCHAR(255) NOT NULL,
    imagen_perfil  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_usuarios PRIMARY KEY (id)
);

ALTER TABLE usuarios
    ADD CONSTRAINT uc_usuarios_correo UNIQUE (correo);

ALTER TABLE tareas
    ADD CONSTRAINT FK_TAREAS_ON_PROYECTO FOREIGN KEY (proyecto_id) REFERENCES proyectos (id);

ALTER TABLE tareas_usuarios
    ADD CONSTRAINT fk_tarusu_on_task FOREIGN KEY (tarea_id) REFERENCES tareas (id);

ALTER TABLE tareas_usuarios
    ADD CONSTRAINT fk_tarusu_on_user FOREIGN KEY (usuario_id) REFERENCES usuarios (id);