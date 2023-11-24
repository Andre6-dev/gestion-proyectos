CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- PROYECTOS
INSERT INTO proyectos
(id, creado_en, modificado_en, nombre_proyecto, descripcion_proyecto, tipo_proyecto, fecha_inicio, fecha_fin, logo)
VALUES (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 1', 'Descripción del Proyecto 1', 'WEB',
        '2023-01-01', '2023-12-31', 'logo1.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 2', 'Descripción del Proyecto 2', 'WEB',
        '2023-02-01', '2023-12-31', 'logo2.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 3', 'Descripción del Proyecto 3', 'MOBILE',
        '2023-03-01', '2023-12-31', 'logo3.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 4', 'Descripción del Proyecto 4', 'DESKTOP',
        '2023-04-01', '2023-12-31', 'logo4.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 5', 'Descripción del Proyecto 5', 'DESKTOP',
        '2023-05-01', '2023-12-31', 'logo5.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Proyecto 6', 'Descripción del Proyecto 6', 'EMBEDDED',
        '2023-06-01', '2023-12-31', 'logo6.png');

-- USUARIOS
INSERT INTO usuarios
(id, creado_en, modificado_en, nombre_usuario, correo, tipo_usuario, telefono, imagen_perfil)
VALUES (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Usuario 1', 'usuario1@mail.com', 'DEVELOPER',
        '1234567890', 'imagen1.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Usuario 2', 'usuario2@mail.com', 'DEVELOPER',
        '1234567891', 'imagen2.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Usuario 3', 'usuario3@mail.com', 'DEVELOPER',
        '1234567892', 'imagen3.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Usuario 4', 'usuario4@mail.com', 'ADMINISTRATOR',
        '1234567893', 'imagen4.png'),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Usuario 5', 'usuario5@mail.com', 'CLIENT',
        '1234567894', 'imagen5.png');

-- TAREAS
INSERT INTO tareas
(id, creado_en, modificado_en, nombre_tarea, descripcion_tarea, estado_tarea, prioridad_tarea, tipo_tarea, fecha_inicio,
 fecha_fin, tiempo_estimado, proyecto_id)
VALUES (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 1', 'Descripción 1', 'PENDING', 'LOW',
        'BACKEND', '2023-01-01', '2023-01-11', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 1')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 2', 'Descripción 2', 'PENDING', 'MEDIUM',
        'FRONTEND', '2023-01-12', '2023-01-22', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 1')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 3', 'Descripción 3', 'PENDING', 'HIGH',
        'BACKEND', '2023-01-23', '2023-02-02', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 2')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 4', 'Descripción 4', 'IN_PROGRESS', 'LOW',
        'MOBILE', '2023-02-03', '2023-02-13', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 2')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 5', 'Descripción 5', 'IN_PROGRESS', 'MEDIUM',
        'BACKEND', '2023-02-14', '2023-02-24', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 3')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 6', 'Descripción 6', 'IN_PROGRESS', 'HIGH',
        'DESKTOP', '2023-02-25', '2023-03-07', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 4')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 7', 'Descripción 7', 'PENDING', 'LOW',
        'BACKEND', '2023-03-08', '2023-03-18', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 5')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 8', 'Descripción 8', 'FINISHED', 'MEDIUM',
        'DEPLOYMENT', '2023-03-19', '2023-03-29', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 6')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 9', 'Descripción 9', 'FINISHED', 'HIGH',
        'FRONTEND', '2023-03-30', '2023-04-09', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 5')),
       (UUID_GENERATE_V4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tarea 10', 'Descripción 10', 'CANCELED', 'HIGH',
        'OTHER', '2023-04-10', '2023-04-20', '10 días',
        (SELECT id FROM proyectos WHERE nombre_proyecto = 'Proyecto 3'));

-- TAREAS-USUARIOS
INSERT INTO tareas_usuarios
(tarea_id, usuario_id)
VALUES ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 1'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 1')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 2'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 1')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 3'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 2')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 3'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 3')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 4'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 1')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 4'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 4')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 4'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 5')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 5'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 2')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 2'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 3')),
       ((SELECT id FROM tareas WHERE nombre_tarea = 'Tarea 5'),
        (SELECT id FROM usuarios WHERE nombre_usuario = 'Usuario 5'));
