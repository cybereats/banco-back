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
('usuario1', '$2a$12$6bV.Hw7P0wpMJXVvp7NBee8078m49vQatXGDm.t8j/TDEqE9ZNvNa', 'Ismael', 'Garcia', 'Garcia', '12345678A', 'token_demo_123'),
('usuario2', '$2a$12$5KwcHTqSprWEYuOEdD5.ru9SoZ4T/c.16/hnuaU9CI7By1XW3Nld2', 'Ana', 'Lopez', 'Perez', '87654321B', 'token_demo_456'),
('usuario3', '$2a$12$uHB2FjulOTs/xbrFoc1Ggu3gyUiA6EUddme003/k7LLu1QV6zAsh6', 'Marcos', 'Ruiz', 'Sanz', '11223344C', 'token_demo_789');

-- =============================================
-- CUENTAS BANCARIAS
-- =============================================
INSERT INTO cuenta_bancaria (saldo, iban, cliente_id) 
VALUES 
(1500.50, 'ES1234567890123456789012', (SELECT id FROM cliente WHERE login = 'usuario1')),
(500.00,  'ES2222222222222222222222', (SELECT id FROM cliente WHERE login = 'usuario1')),
(3200.00, 'ES9876543210987654321098', (SELECT id FROM cliente WHERE login = 'usuario2')),
(1000.00, 'ES3333333333333333333333', (SELECT id FROM cliente WHERE login = 'usuario2')),
(50.25,   'ES1111222233334444555566', (SELECT id FROM cliente WHERE login = 'usuario3')),
(200.75,  'ES4444444444444444444444', (SELECT id FROM cliente WHERE login = 'usuario3'));

-- =============================================
-- TARJETAS DE CRÉDITO
-- =============================================
INSERT INTO tarjeta_credito (numero_tarjeta, fecha_caducidad, cvc, nombre_completo, cuenta_bancaria_id) 
VALUES 
('1234567890123456', '2028-12-31', 123, 'ISMAEL GARCIA GARCIA', (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('1111111111111111', '2029-01-01', 111, 'ISMAEL GARCIA GARCIA', (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('2222222222222222', '2028-05-15', 222, 'ISMAEL GARCIA GARCIA', (SELECT id FROM cuenta_bancaria WHERE iban = 'ES2222222222222222222222')),
('6543210987654321', '2027-06-30', 456, 'ANA LOPEZ PEREZ',      (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('3333333333333333', '2027-12-31', 333, 'ANA LOPEZ PEREZ',      (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('4444444444444444', '2026-08-20', 444, 'ANA LOPEZ PEREZ',      (SELECT id FROM cuenta_bancaria WHERE iban = 'ES3333333333333333333333')),
('1111222233334444', '2026-01-01', 789, 'MARCOS RUIZ SANZ',    (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1111222233334444555566')),
('5555555555555555', '2027-03-15', 555, 'MARCOS RUIZ SANZ',    (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1111222233334444555566')),
('6666666666666666', '2028-11-11', 666, 'MARCOS RUIZ SANZ',    (SELECT id FROM cuenta_bancaria WHERE iban = 'ES4444444444444444444444'));

-- =============================================
-- MOVIMIENTOS BANCARIOS
-- =============================================
-- Movimientos para Usuario 1
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES 
('HABER', 'TRANSFERENCIA', '2026-01-14 10:00:00', 1000.00, 'Nomina Enero', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-14 12:30:00', 50.25, 'Compra CyberEats', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '1234567890123456'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('DEBE', 'DOMICILIACION', '2026-01-14 15:00:00', 12.99, 'Suscripcion Streaming', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1234567890123456789012')),
('HABER', 'TRANSFERENCIA', '2026-01-14 16:00:00', 500.00, 'Ingreso Efectivo', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES2222222222222222222222')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-14 17:30:00', 25.00, 'Cena Restaurante', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '2222222222222222'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES2222222222222222222222'));

-- Movimientos para Usuario 2
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES 
('HABER', 'TRANSFERENCIA', '2026-01-15 09:00:00', 2500.00, 'Saldo Inicial', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-15 11:00:00', 120.00, 'Amazon Compra', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '6543210987654321'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-15 12:00:00', 45.50, 'Gasolinera', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '3333333333333333'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES9876543210987654321098')),
('HABER', 'TRANSFERENCIA', '2026-01-15 13:00:00', 1000.00, 'Transferencia Recibida', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES3333333333333333333333'));

-- Movimientos para Usuario 3
INSERT INTO movimiento_bancario (tipo_movimiento_bancario, origen_movimiento_bancario, fecha, importe, concepto, tarjeta_credito_id, cuenta_bancaria_id) 
VALUES 
('HABER', 'TRANSFERENCIA', '2026-01-16 08:00:00', 50.25, 'Saldo Apertura', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1111222233334444555566')),
('DEBE', 'TARJETA_BANCARIA', '2026-01-16 09:00:00', 10.00, 'Cafe y Tostada', (SELECT id FROM tarjeta_credito WHERE numero_tarjeta = '5555555555555555'), (SELECT id FROM cuenta_bancaria WHERE iban = 'ES1111222233334444555566')),
('HABER', 'TRANSFERENCIA', '2026-01-16 10:00:00', 200.75, 'Regalo Cumpleaños', NULL, (SELECT id FROM cuenta_bancaria WHERE iban = 'ES4444444444444444444444'));
