-- Crear tabla F01_TIPO_CAMBIO si no existe
CREATE TABLE IF NOT EXISTS F01_TIPO_CAMBIO (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               moneda_origen VARCHAR(50) NOT NULL,
                                               moneda_destino VARCHAR(50) NOT NULL,
                                               tipo_cambio DOUBLE NOT NULL
);

-- Crear tabla F01_USUARIOS si no existe
CREATE TABLE IF NOT EXISTS F01_USUARIOS (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            nombre VARCHAR(100) NOT NULL,
                                            apellidos VARCHAR(150) NOT NULL,
                                            telefono VARCHAR(20) NOT NULL,
                                            celular VARCHAR(20) NOT NULL
);

-- Crear tabla F01_ROLES si no existe
CREATE TABLE IF NOT EXISTS F01_ROLES (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(50) NOT NULL
);

-- Crear tabla F01_CREDENCIALES si no existe
CREATE TABLE IF NOT EXISTS F01_CREDENCIALES (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                usuario_id BIGINT NOT NULL,
                                                rol_id BIGINT NOT NULL,
                                                username VARCHAR(100) NOT NULL UNIQUE,
                                                password VARCHAR(200) NOT NULL,
                                                FOREIGN KEY (usuario_id) REFERENCES F01_USUARIOS(id),
                                                FOREIGN KEY (rol_id) REFERENCES F01_ROLES(id)
);

-- Crear tabla F01_AUDITORIA si no existe
CREATE TABLE IF NOT EXISTS F01_AUDITORIA (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             accion VARCHAR(50) NOT NULL,
                                             descripcion VARCHAR(255) NOT NULL,
                                             username VARCHAR(100) NOT NULL,
                                             rol VARCHAR(50) NOT NULL,
                                             moneda_origen VARCHAR(10) NOT NULL,
                                             moneda_destino VARCHAR(10) NOT NULL,
                                             tipo_cambio DOUBLE NOT NULL,
                                             monto_original DOUBLE NOT NULL,
                                             monto_convertido DOUBLE NOT NULL,
                                             fecha VARCHAR(50)
);

-- Insertar roles si no existen
INSERT INTO F01_ROLES (id, nombre)
SELECT * FROM (SELECT 1 AS id, 'Administrador' AS nombre) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_ROLES WHERE id = 1)
UNION
SELECT * FROM (SELECT 2 AS id, 'Cliente' AS nombre) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_ROLES WHERE id = 2)
UNION
SELECT * FROM (SELECT 3 AS id, 'Apoyo' AS nombre) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_ROLES WHERE id = 3);

-- Insertar tipos de cambio si no existen
INSERT INTO F01_TIPO_CAMBIO (id, moneda_origen, moneda_destino, tipo_cambio)
SELECT * FROM (SELECT 4, 'GBP', 'PEN', 4.50) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 4)
UNION
SELECT * FROM (SELECT 5, 'JPY', 'PEN', 0.03) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 5)
UNION
SELECT * FROM (SELECT 6, 'CAD', 'PEN', 3.10) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 6)
UNION
SELECT * FROM (SELECT 7, 'AUD', 'PEN', 2.90) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 7)
UNION
SELECT * FROM (SELECT 8, 'PEN', 'GBP', 0.22) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 8)
UNION
SELECT * FROM (SELECT 9, 'PEN', 'JPY', 33.33) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 9)
UNION
SELECT * FROM (SELECT 10, 'PEN', 'CAD', 0.32) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 10)
UNION
SELECT * FROM (SELECT 11, 'PEN', 'AUD', 0.34) AS tmp
WHERE NOT EXISTS (SELECT id FROM F01_TIPO_CAMBIO WHERE id = 11);

COMMIT;
