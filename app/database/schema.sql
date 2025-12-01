-- Script de creación de base de datos para Sistema de Reserva de Salas

CREATE DATABASE IF NOT EXISTS reserva_salas;
USE reserva_salas;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

-- Tabla de salas
CREATE TABLE IF NOT EXISTS salas (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    capacidad INT NOT NULL
);

-- Tabla de reservas
CREATE TABLE IF NOT EXISTS reservas (
    id VARCHAR(50) PRIMARY KEY,
    sala_id VARCHAR(50) NOT NULL,
    usuario_id VARCHAR(50) NOT NULL,
    inicio DATETIME NOT NULL,
    fin DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (sala_id) REFERENCES salas(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    INDEX idx_sala_fecha (sala_id, inicio, fin)
);

-- Datos de ejemplo
INSERT INTO usuarios (id, nombre, rol) VALUES 
    ('U1', 'Ana', 'Empleado'),
    ('U2', 'Carlos', 'Gerente'),
    ('U3', 'María', 'Empleado')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO salas (id, nombre, capacidad) VALUES 
    ('S1', 'Sala A', 6),
    ('S2', 'Sala B', 10),
    ('S3', 'Sala C', 4)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);
