-- 1. Tabla ALMACENES
CREATE TABLE IF NOT EXISTS almacenes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    ciudad VARCHAR(100)
    );

-- 2. Tabla PRODUCTOS
CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2)
    );

-- 3. Tabla INVENTARIO (Relación Muchos a Muchos)
CREATE TABLE IF NOT EXISTS inventario (
    id SERIAL PRIMARY KEY,
    almacen_id INTEGER NOT NULL REFERENCES almacenes(id) ON DELETE CASCADE,
    producto_id INTEGER NOT NULL REFERENCES productos(id) ON DELETE CASCADE,
    cantidad INTEGER NOT NULL DEFAULT 0,
    UNIQUE (almacen_id, producto_id)
    );

-- --- DATOS DE PRUEBA ---

-- Creamos dos sedes
INSERT INTO almacenes (nombre, ciudad) VALUES
('Sede Central', 'Bogotá'),
('Sede Norte', 'Medellín');

-- Creamos dos productos
INSERT INTO productos (nombre, precio) VALUES
('Laptop Dell', 2500.00),
('Mouse Logitech', 50.00);

-- Llenamos el inventario
-- En la Sede Central (1) hay 10 Laptops (1)
INSERT INTO inventario (almacen_id, producto_id, cantidad) VALUES (1, 1, 10);
-- En la Sede Norte (2) hay 20 Mouses (2)
INSERT INTO inventario (almacen_id, producto_id, cantidad) VALUES (2, 2, 20);