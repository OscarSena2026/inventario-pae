-- ============================================================
-- Sistema de Inventario PAE - Esquema relacional MySQL
-- Basado en el modelo entidad-relación de AA1 (Diagrama_ERR_PAE.mwb)
-- GA7-220501096-AA2-EV01
-- ============================================================

CREATE DATABASE IF NOT EXISTS inventario_pae
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE inventario_pae;

-- ---------- Tablas maestras ----------

CREATE TABLE municipio (
    id_municipio     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_municipio VARCHAR(60) NOT NULL
);

CREATE TABLE proveedor (
    id_proveedor     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proveedor VARCHAR(100) NOT NULL,
    nit              VARCHAR(15) NOT NULL,
    telefono         VARCHAR(15),
    direccion        VARCHAR(100)
);

CREATE TABLE empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombres     VARCHAR(100) NOT NULL,
    cargo       VARCHAR(40),
    telefono    VARCHAR(15)
);

CREATE TABLE producto (
    id_producto    INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(80) NOT NULL,
    categoria      VARCHAR(40),
    unidad_medida  VARCHAR(15) NOT NULL,
    perecedero     ENUM('SI','NO') NOT NULL DEFAULT 'SI'
);

CREATE TABLE sede (
    id_sede       INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sede   VARCHAR(100) NOT NULL,
    direccion     VARCHAR(100),
    id_municipio  INT NOT NULL,
    CONSTRAINT fk_sede_municipio FOREIGN KEY (id_municipio) REFERENCES municipio(id_municipio)
);

CREATE TABLE bodega (
    id_bodega     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_bodega VARCHAR(80) NOT NULL,
    direccion     VARCHAR(100),
    id_municipio  INT NOT NULL,
    CONSTRAINT fk_bodega_municipio FOREIGN KEY (id_municipio) REFERENCES municipio(id_municipio)
);

-- ---------- Tablas transaccionales ----------

CREATE TABLE lote (
    id_lote            INT AUTO_INCREMENT PRIMARY KEY,
    fecha_fabricacion  DATE,
    fecha_vencimiento  DATE NOT NULL,
    cantidad_recibida  DECIMAL(10,2) NOT NULL,
    id_producto        INT NOT NULL,
    id_proveedor       INT NOT NULL,
    CONSTRAINT fk_lote_producto  FOREIGN KEY (id_producto)  REFERENCES producto(id_producto),
    CONSTRAINT fk_lote_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

CREATE TABLE entrada (
    id_entrada      INT AUTO_INCREMENT PRIMARY KEY,
    fecha_entrada   DATE NOT NULL,
    numero_factura  VARCHAR(20),
    id_bodega       INT NOT NULL,
    id_proveedor    INT NOT NULL,
    id_empleado     INT NOT NULL,
    CONSTRAINT fk_entrada_bodega    FOREIGN KEY (id_bodega)    REFERENCES bodega(id_bodega),
    CONSTRAINT fk_entrada_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    CONSTRAINT fk_entrada_empleado  FOREIGN KEY (id_empleado)  REFERENCES empleado(id_empleado)
);

CREATE TABLE detalle_entrada (
    id_detalle_entrada INT AUTO_INCREMENT PRIMARY KEY,
    id_entrada         INT NOT NULL,
    id_lote            INT NOT NULL,
    cantidad           DECIMAL(10,2) NOT NULL,
    valor_unitario     DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detentrada_entrada FOREIGN KEY (id_entrada) REFERENCES entrada(id_entrada),
    CONSTRAINT fk_detentrada_lote    FOREIGN KEY (id_lote)    REFERENCES lote(id_lote)
);

CREATE TABLE salida (
    id_salida     INT AUTO_INCREMENT PRIMARY KEY,
    fecha_salida  DATE NOT NULL,
    motivo        VARCHAR(100),
    id_bodega     INT NOT NULL,
    id_sede       INT NOT NULL,
    id_empleado   INT NOT NULL,
    CONSTRAINT fk_salida_bodega   FOREIGN KEY (id_bodega)   REFERENCES bodega(id_bodega),
    CONSTRAINT fk_salida_sede     FOREIGN KEY (id_sede)     REFERENCES sede(id_sede),
    CONSTRAINT fk_salida_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

CREATE TABLE detalle_salida (
    id_detalle_salida INT AUTO_INCREMENT PRIMARY KEY,
    id_salida         INT NOT NULL,
    id_lote           INT NOT NULL,
    cantidad          DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detsalida_salida FOREIGN KEY (id_salida) REFERENCES salida(id_salida),
    CONSTRAINT fk_detsalida_lote   FOREIGN KEY (id_lote)   REFERENCES lote(id_lote)
);

CREATE TABLE existencia (
    id_existencia       INT AUTO_INCREMENT PRIMARY KEY,
    cantidad_disponible DECIMAL(10,2) NOT NULL,
    id_bodega           INT NOT NULL,
    id_lote             INT NOT NULL,
    CONSTRAINT fk_existencia_bodega FOREIGN KEY (id_bodega) REFERENCES bodega(id_bodega),
    CONSTRAINT fk_existencia_lote   FOREIGN KEY (id_lote)   REFERENCES lote(id_lote)
);
