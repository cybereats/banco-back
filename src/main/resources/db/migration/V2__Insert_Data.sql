-- Limpiar datos previos si existen (Opcional, depende de si quieres empezar de cero)
-- DELETE FROM movimiento_bancario;
-- DELETE FROM tarjeta_credito;
-- DELETE FROM cuenta_bancaria;
-- DELETE FROM cliente;

-- =============================================
-- CLIENTES
-- =============================================
INSERT INTO cliente (login, password, nombre, apellido1, apellido2, dni, api_token) 
VALUES 
('usuario1', '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgOtT7P6D82DUcjh.98Q7Vdfp.8q', 'Ismael', 'Garcia', 'Garcia', '12345678A', 'token_demo_123'),
('usuario2', '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgOtT7P6D82DUcjh.98Q7Vdfp.8q', 'Ana', 'Lopez', 'Perez', '87654321B', 'token_demo_456'),
('usuario3', '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgOtT7P6D82DUcjh.98Q7Vdfp.8q', 'Marcos', 'Ruiz', 'Sanz', '11223344C', 'token_demo_789');

-- =============================================
-- CUENTAS BANCARIAS
-- =============================================
INSERT INTO cuenta_bancaria (saldo, iban, cliente_id) 
VALUES 
(1500.50, 'ES1234567890123456789012', (SELECT id FROM cliente WHERE login = 'usuario1')),
(3200.00, 'ES9876543210987654321098', (SELECT id FROM cliente WHERE login = 'usuario2')),
(50.25,   'ES1111222233334444555566', (SELECT id FROM cliente WHERE login = 'usuario3'));

-- =============================================
-- TARJETAS DE CRÉDITO
-- =============================================
INSERT INTO tarjeta_credito (numero_tarjeta, fecha_caducidad, cvc, nombre_completo, cuenta_bancaria_id) 
VALUES 
('1234567890123456', '2028-12-31', 123, 'ISMAEL GARCIA GARCIA', (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('6543210987654321', '2027-06-30', 456, 'ANA LOPEZ PEREZ',      (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('1111222233334444', '2026-01-01', 789, 'MARCOS RUIZ SANZ',    (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1111222233334444555566'));

-- =============================================
-- MOVIMIENTOS BANCARIOS
-- =============================================
-- Movimientos para Usuario 1
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES 
('HABER', 'TRANSFERENCIA', '2026-01-14 10:00:00', 1000.00, 'Nomina Enero', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-14 12:30:00', 50.25, 'Compra CyberEats', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '1234567890123456'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('DEBE', 'DOMICILIACION', '2026-01-14 15:00:00', 12.99, 'Suscripcion Streaming', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012'));

-- Movimientos para Usuario 2
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES 
('HABER', 'TRANSFERENCIA', '2026-01-15 09:00:00', 2500.00, 'Saldo Inicial', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-15 11:00:00', 120.00, 'Amazon Compra', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '6543210987654321'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098'));
