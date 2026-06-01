CREATE TABLE IF NOT EXISTS ciudadano (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(100),
    direccion VARCHAR(200) NOT NULL,
    genero VARCHAR(15)
);

ALTER TABLE ciudadano ADD COLUMN IF NOT EXISTS correo VARCHAR(100);

CREATE TABLE IF NOT EXISTS funcionario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    credenciales VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tipo_denuncia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS denuncia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP NOT NULL,
    ubicacion VARCHAR(200) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    ciudadano_id BIGINT NOT NULL,
    tipo_id BIGINT NOT NULL,
    funcionario_id BIGINT,
    FOREIGN KEY (ciudadano_id) REFERENCES ciudadano(id),
    FOREIGN KEY (tipo_id) REFERENCES tipo_denuncia(id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);


MERGE INTO funcionario (id, nombre, cargo, credenciales)
KEY(id) VALUES (1, 'admin', 'Administrador', 'admin');

MERGE INTO tipo_denuncia (id, nombre)
KEY(id) VALUES 
(1, 'Robo / Asalto'),
(2, 'Accidente de Tránsito'),
(3, 'Vandalismo'),
(4, 'Violencia Familiar'),
(5, 'Fraude / Estafa');

MERGE INTO ciudadano (id, nombre, dni, telefono, correo, direccion, genero)
KEY(id) VALUES (1, 'Juan Carlos Quispe Mamani', '70123456', '951456789', 'juan.quispe@email.com', 'Jr. Moquegua 450, Juliaca', 'MASCULINO');

MERGE INTO denuncia (id, descripcion, fecha, ubicacion, estado, ciudadano_id, tipo_id, funcionario_id)
KEY(id) VALUES (1, 'Robo de teléfono celular (Samsung Galaxy S23) mediante asalto a mano armada por parte de dos sujetos a bordo de una motocicleta lineal, mientras transitaba por las inmediaciones de la Plaza de Armas de Juliaca.', '2026-06-01 08:00:00', 'Esquina de Jr. Jáuregui con Jr. Mariano Núñez (cerca a la Plaza de Armas), Juliaca', 'PENDIENTE', 1, 1, 1);