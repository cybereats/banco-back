-- Insert Client
INSERT INTO cliente (login, password, nombre, apellido1, apellido2, dni, api_token) 
VALUES ('usuario1', '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgOtT7P6D82DUcjh.98Q7Vdfp.8q', 'Ismael', 'Garcia', 'Garcia', '12345678A', 'token_demo_123');

-- Insert Bank Account
INSERT INTO cuenta_bancaria (saldo, iban, cliente_id) 
VALUES (1500.50, 'ES1234567890123456789012', 1);

-- Insert Credit Card
INSERT INTO tarjeta_credito (numero_tarjeta, fecha_caducidad, cvc, nombre_completo, cuenta_bancaria_id) 
VALUES ('1234567890123456', '2028-12-31', 123, 'ISMAEL GARCIA GARCIA', 1);

-- Insert Movements
-- HABER (Income)
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES ('HABER', 'TRANSFERENCIA', '2026-01-14 10:00:00', 1000.00, 'Nomina Enero', NULL, 1);

-- DEBE (Expense)
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES ('DEBE', 'TARJETA_BANCARIA', '2026-01-14 12:30:00', 50.25, 'Compra CyberEats', 1, 1);

-- DEBE (Another Expense)
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES ('DEBE', 'DOMICILIACION', '2026-01-14 15:00:00', 12.99, 'Suscripcion Streaming', NULL, 1);
