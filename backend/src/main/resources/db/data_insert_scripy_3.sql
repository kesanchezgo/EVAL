-- Insertar la evaluación "Evaluación de proyectos de investigación"
INSERT INTO evaluations (name, description, evaluation_scale) VALUES
    ('Evaluación de proyectos de investigación', 'Evaluación de proyectos de investigación', 20);

-- Insertar los criterios y subcriterios
INSERT INTO criteria (name, description, weight) VALUES
    ('Necesidad', 'Demanda real que el proyecto satisface, justificando su desarrollo al resolver problemas u ofrecer oportunidades.', 30),
    ('Potencial de Aplicación en el Mercado', 'Capacidad del producto para ser comercializado con éxito.', 28),
    ('Innovación y Diferenciación', 'Originalidad y singularidad del proyecto en comparación con otros.', 25),
    ('Factibilidad Económica', 'Viabilidad financiera del proyecto a nivel nacional.', 17);

INSERT INTO subcriteria (name, description, weight, range1, range2) VALUES
    ('El producto no resuelve ninguna necesidad identificada.', 'El producto no resuelve ninguna necesidad identificada.', 100, 0, 3),
    ('Existe una necesidad, pero el producto no es completamente necesario para abordarla.', 'Existe una necesidad, pero el producto no es completamente necesario para abordarla.', 100, 4, 7),
    ('El producto resuelve una necesidad específica, pero podría ser mejorado para ser más efectivo.', 'El producto resuelve una necesidad específica, pero podría ser mejorado para ser más efectivo.', 100, 8, 13),
    ('El producto satisface una necesidad crítica y es esencial para abordar el problema identificado.', 'El producto satisface una necesidad crítica y es esencial para abordar el problema identificado.', 100, 14, 20),
    ('No se ha evaluado el potencial de aplicación de los resultados en el mercado y no se identifican posibles aplicaciones prácticas.', 'No se ha evaluado el potencial de aplicación de los resultados en el mercado y no se identifican posibles aplicaciones prácticas.', 100, 0, 3),
    ('La evaluación del potencial de aplicación en el mercado es limitada y las posibles aplicaciones prácticas son poco claras.', 'La evaluación del potencial de aplicación en el mercado es limitada y las posibles aplicaciones prácticas son poco claras.', 100, 4, 7),
    ('Se ha evaluado el potencial de aplicación en el mercado, pero las aplicaciones prácticas necesitan algunas mejoras o especificaciones adicionales.', 'Se ha evaluado el potencial de aplicación en el mercado, pero las aplicaciones prácticas necesitan algunas mejoras o especificaciones adicionales.', 100, 8, 13),
    ('La evaluación del potencial de aplicación en el mercado es sólida y se identifican claramente las aplicaciones prácticas de los resultados del proyecto.', 'La evaluación del potencial de aplicación en el mercado es sólida y se identifican claramente las aplicaciones prácticas de los resultados del proyecto.', 100, 14, 20),
    ('La propuesta carece de innovación y no presenta diferenciación, ya que existen soluciones similares en el mercado.', 'La propuesta carece de innovación y no presenta diferenciación, ya que existen soluciones similares en el mercado.', 100, 0, 3),
    ('Se identifica una innovación, pero está disponible internacionalmente y no a nivel nacional, lo que limita la originalidad local.', 'Se identifica una innovación, pero está disponible internacionalmente y no a nivel nacional, lo que limita la originalidad local.', 100, 4, 7),
    ('La propuesta presenta una innovación nueva sin equivalentes en el mercado actual.', 'La propuesta presenta una innovación nueva sin equivalentes en el mercado actual.', 100, 8, 13),
    ('La innovación es nueva, responde a una necesidad específica del mercado y demuestra ser requerida y potencialmente exitosa.', 'La innovación es nueva, responde a una necesidad específica del mercado y demuestra ser requerida y potencialmente exitosa.', 100, 14, 20),
    ('La factibilidad económica del proyecto a nivel nacional es muy baja o no está claramente definida.', 'La factibilidad económica del proyecto a nivel nacional es muy baja o no está claramente definida.', 100, 0, 3),
    ('Existe cierta viabilidad económica a nivel nacional, pero hay aspectos significativos que podrían dificultar su implementación a gran escala.', 'Existe cierta viabilidad económica a nivel nacional, pero hay aspectos significativos que podrían dificultar su implementación a gran escala.', 100, 4, 7),
    ('El proyecto tiene una factibilidad económica a nivel nacional aceptable, con algunos riesgos y consideraciones que podrían afectar su viabilidad en ciertas circunstancias.', 'El proyecto tiene una factibilidad económica a nivel nacional aceptable, con algunos riesgos y consideraciones que podrían afectar su viabilidad en ciertas circunstancias.', 100, 8, 13),
    ('La factibilidad económica del proyecto a nivel nacional es alta y sólida, con un claro potencial para generar beneficios económicos significativos a gran escala y minimizar riesgos.', 'La factibilidad económica del proyecto a nivel nacional es alta y sólida, con un claro potencial para generar beneficios económicos significativos a gran escala y minimizar riesgos.', 100, 14, 20);


-- Establecer relaciones entre criterios y subcriterios
INSERT INTO subcriteria_criteria (criteria_id, subcriteria_id) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4),
    (2, 5), (2, 6), (2, 7), (2, 8),
    (3, 9), (3, 10), (3, 11), (3, 12),
    (4, 13), (4, 14), (4, 15), (4, 16);

-- Establecer relaciones entre criterios y evaluación
INSERT INTO criteria_evaluations (criteria_id, evaluation_id) VALUES
    (1, 1), (2, 1), (3, 1), (4, 1);
