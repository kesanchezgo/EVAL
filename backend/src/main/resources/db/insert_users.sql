-- Insertar usuarios adicionales
INSERT INTO users (email, "password", username)
VALUES 
    ('ghuarcayal@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'ghuarcayal'),
    ('avalcarcelc@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'avalcarcelc'),
    ('cnunezcar@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'cnunezcar'),
    ('jsilvaf@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'jsilvaf'),
    ('balvarezo@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'balvarezo'),
    ('jcutizaca@unsa.edu.pe', '$2a$10$90szlW3gOAghwyMvpMa5MOrTlXs65ma33uyRhKG2GTdtI6qH/7Ieq', 'jcutizaca');

-- Insertar roles para cada usuario
INSERT INTO user_roles (user_id, role_id)
SELECT id, 1 FROM users WHERE username = 'ghuarcayal' UNION ALL
SELECT id, 2 FROM users WHERE username = 'ghuarcayal' UNION ALL
SELECT id, 3 FROM users WHERE username = 'ghuarcayal' UNION ALL
SELECT id, 1 FROM users WHERE username = 'avalcarcelc' UNION ALL
SELECT id, 2 FROM users WHERE username = 'avalcarcelc' UNION ALL
SELECT id, 3 FROM users WHERE username = 'avalcarcelc' UNION ALL
SELECT id, 1 FROM users WHERE username = 'cnunezcar' UNION ALL
SELECT id, 2 FROM users WHERE username = 'cnunezcar' UNION ALL
SELECT id, 3 FROM users WHERE username = 'cnunezcar' UNION ALL
SELECT id, 1 FROM users WHERE username = 'jsilvaf' UNION ALL
SELECT id, 2 FROM users WHERE username = 'jsilvaf' UNION ALL
SELECT id, 3 FROM users WHERE username = 'jsilvaf' UNION ALL
SELECT id, 1 FROM users WHERE username = 'balvarezo' UNION ALL
SELECT id, 2 FROM users WHERE username = 'balvarezo' UNION ALL
SELECT id, 3 FROM users WHERE username = 'balvarezo' UNION ALL
SELECT id, 1 FROM users WHERE username = 'jcutizaca' UNION ALL
SELECT id, 2 FROM users WHERE username = 'jcutizaca' UNION ALL
SELECT id, 3 FROM users WHERE username = 'jcutizaca';
