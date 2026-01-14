CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(255),
    apellido1 VARCHAR(255),
    apellido2 VARCHAR(255),
    dni VARCHAR(255),
    api_token VARCHAR(255)
);

CREATE TABLE cuenta_bancaria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    saldo DECIMAL(19, 2),
    iban VARCHAR(255),
    cliente_id INT,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE tarjeta_credito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_tarjeta VARCHAR(255),
    fecha_caducidad DATE,
    cvc INT,
    nombre_completo VARCHAR(255),
    cuenta_bancaria_id INT,
    CONSTRAINT fk_tarjeta_cuenta FOREIGN KEY (cuenta_bancaria_id) REFERENCES cuenta_bancaria(id)
);

CREATE TABLE movimiento_bancario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_movimiento_bancario VARCHAR(255),
    origen_movimiento_bancario VARCHAR(255),
    fecha DATETIME,
    importe DECIMAL(19, 2),
    concepto VARCHAR(255),
    tarjeta_credito_id INT,
    cuenta_bancaria_id INT,
    CONSTRAINT fk_movimiento_tarjeta FOREIGN KEY (tarjeta_credito_id) REFERENCES tarjeta_credito(id),
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_bancaria_id) REFERENCES cuenta_bancaria(id)
);
