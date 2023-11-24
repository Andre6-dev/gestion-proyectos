ALTER TABLE proyectos
    ALTER COLUMN descripcion_proyecto DROP NOT NULL;

ALTER TABLE tareas
    ALTER COLUMN descripcion_tarea DROP NOT NULL;