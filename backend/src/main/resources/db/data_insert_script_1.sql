-- Insertar datos de prueba en la tabla 'evaluations'
INSERT INTO evaluations (name, description, evaluation_scale) VALUES
    ('Evaluación 1', 'Descripción de la evaluación 1', 20),
    ('Evaluación 2', 'Descripción de la evaluación 2', 20);

-- Insertar datos de prueba en la tabla 'subcriteria'
INSERT INTO subcriteria (name, description, weight) VALUES
    ('Subcriterio 1', 'Descripción del subcriterio 1', 25),
    ('Subcriterio 2', 'Descripción del subcriterio 2', 25),
    ('Subcriterio 3', 'Descripción del subcriterio 3', 25),
    ('Subcriterio 4', 'Descripción del subcriterio 4', 25);

-- Insertar datos de prueba en la tabla 'criteria'
INSERT INTO criteria (name, description, weight) VALUES
    ('Criterio 1', 'Descripción del criterio 1', 50),
    ('Criterio 2', 'Descripción del criterio 2', 50);

-- Insertar datos de prueba en la tabla 'subcriteria_criteria'
INSERT INTO subcriteria_criteria (criteria_id, subcriteria_id) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), -- Criterio 1 con sus subcriterios
    (2, 1), (2, 2), (2, 3), (2, 4); -- Criterio 2 con sus subcriterios

-- Insertar datos de prueba en la tabla 'criteria_evaluations'
INSERT INTO criteria_evaluations (criteria_id, evaluation_id) VALUES
    (1, 1), (1, 2), -- Criterio 1 con evaluaciones
    (2, 1), (2, 2); -- Criterio 2 con evaluaciones
