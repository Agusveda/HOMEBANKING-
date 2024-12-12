
-- Tabla cliente
INSERT INTO tpint_grupo_6_lab4.cliente (Id, DNI, CUIL, Nombre, Apellido, Sexo, Nacionalidad, FechaNacimiento, Direccion, Localidad, Provincia, CorreoElectronico, Telefono, Activo) VALUES 
(1, '12345678', '2012345678', 'Juan', 'Perez', 'M', 'Argentina', '1985-06-15', 'Calle Falsa 123', 'Tigre', 'Buenos Aires', 'juan.perez@example.com', '011123456', 1),
(2, '23456789', '2023456789', 'Maria', 'Gomez', 'F', 'Argentina', '1990-12-01', 'Avenida  742', 'Rosario', 'Santa Fe', 'maria.gomez@example.com', '034112345', 1),
(3, '34567890', '2034567890', 'Carlos', 'Lopez', 'M', 'Argentina', '1978-03-22', 'Paseo Colon 123', 'Cordoba', 'Cordoba', 'carlos.lopez@example.com', '035112345', 1),
(4, '45678901', '2045678901', 'Ana', 'Martinez', 'F', 'Argentina', '1983-08-13', 'Belgrano 456', 'La Plata', 'Buenos Aires', 'ana.martinez@example.com', '022112345', 1),
(5, '56789012', '2056789012', 'Luis', 'Rodriguez', 'M', 'Argentina', '1975-11-30', 'San Martin 789', 'Mendoza', 'Mendoza', 'luis.rodriguez@example.com', '026112367', 1),
(6, '67890123', '2067890123', 'Laura', 'Fernandez', 'F', 'Argentina', '1989-04-07', 'Sarmiento 123', 'Tucuman', 'Tucuman', 'laura.fernandez@example.com', '038112567', 1),
(7, '78901234', '2078901234', 'Jorge', 'Garcia', 'M', 'Argentina', '1982-02-20', 'Rivadavia 456', 'Salta', 'Salta', 'jorge.garcia@example.com', '038712367', 1),
(8, '89012345', '2089012345', 'Sofia', 'Romero', 'F', 'Argentina', '1995-09-09', 'Mitre 789', 'Posadas', 'Misiones', 'sofia.romero@example.com', '037612567', 1),
(9, '90123456', '2090123456', 'Pedro', 'Diaz', 'M', 'Argentina', '1991-01-19', 'Pellegrini 123', 'San Juan', 'San Juan', 'pedro.diaz@example.com', '026434567', 1),
(10, '01234567', '2001234567', 'Marta', 'Suarez', 'F', 'Argentina', '1980-07-25', 'Güemes 456', 'San Luis', 'San Luis', 'marta.suarez@example.com', '026634567', 1);

-- Tabla usuario
INSERT INTO tpint_grupo_6_lab4.usuario (Id, NombreUsuario, Contraseña, TipoUsario, IdCliente, Activo) VALUES 
(1, 'juanperez', 'Perez1234', 0, 1, 1),
(2, 'mariagomez', 'gomez1234', 0, 2, 1),
(3, 'carloslopez', 'lopez1234', 0, 3, 1),
(4, 'anamarinez', 'marinez1234', 0, 4, 1),
(5, 'luisrodriguez', 'rodri321', 0, 5, 1),
(6, 'laurafernandez', 'ferlau123', 0, 6, 1),
(7, 'jorgegarcia', 'jor1234', 0, 7, 1),
(8, 'sofiaromero', 'sofiro23', 0, 8, 1),
(9, 'pedrodiaz', 'pitterQuiroz', 0, 9, 1),
(10, 'admin1', 'adminpass', 1, NULL, 1);

-- Tabla cuenta
INSERT INTO tpint_grupo_6_lab4.cuenta (Id, IdCliente, TipoCuenta, FechaCreacion, NumeroCuenta, CBU, Saldo, Activo) VALUES 
(1, 1, 1, '2024-12-11', 1001, '31001001', 10000, 1),
(2, 1, 2, '2024-12-11', 1002, '31001002', 10000, 1),
(3, 2, 1, '2024-12-11', 1003, '31005001', 10000, 1),
(4, 3, 2, '2024-12-11', 1004, '310003001', 10000, 1),
(5, 3, 1, '2024-12-11', 1005, '31003002', 10000, 1),
(6, 4, 1, '2024-12-11', 1006, '31004001', 10000, 1),
(7, 5, 2, '2024-12-11', 1007, '31005001', 10000, 1),
(8, 5, 1, '2024-12-11', 1008, '31005002', 10000, 1),
(9, 6, 1, '2024-12-11', 1009, '3106001', 10000, 1),
(10, 7, 2, '2024-12-11', 1010, '3107001', 10000, 1);

-- Tabla movimiento
INSERT INTO tpint_grupo_6_lab4.movimiento (Id, TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) VALUES 
(1, 1, '2024-12-11', 10000, 1, 'Alta de cuenta'),
(2, 1, '2024-12-11', 10000, 2, 'Alta de cuenta'),
(3, 1, '2024-12-11', 10000, 3, 'Alta de cuenta'),
(4, 1, '2024-12-11', 10000, 4, 'Alta de cuenta'),
(5, 1, '2024-12-11', 10000, 5, 'Alta de cuenta'),
(6, 1, '2024-12-11', 10000, 6, 'Alta de cuenta'),
(7, 1, '2024-12-11', 10000, 7, 'Alta de cuenta'),
(8, 1, '2024-12-11', 10000, 8, 'Alta de cuenta'),
(9, 1, '2024-12-11', 10000, 9, 'Alta de cuenta'),
(10, 1, '2024-12-11', 10000, 10, 'Alta de cuenta');

-- Tabla prestamo
INSERT INTO tpint_grupo_6_lab4.prestamo (Id, IdCliente, ImportePedidoCliente, FechaAlta, PlazoPago, ImportePagarXmes, CantidadCuotas, confirmacion) VALUES 
(1, 1, 50000, '2024-12-01', 3 , 5000, 3, 1),
(2, 2, 30000, '2024-12-01',6 , 5000, 6, 1),
(3, 3, 100000, '2024-12-01', 12, 5000, 12, 1),
(4, 4, 20000, '2024-12-01', 9, 2000, 9, 1),
(5, 5, 40000, '2024-12-01', 12, 3333, 12, 1);

-- Tabla cuota
INSERT INTO tpint_grupo_6_lab4.cuota (Id, IdPrestamo, NumeroCuota, Monto, FechaPago, estaPagada) VALUES 
(1, 1, 1, 5000, '2025-01-01', 0),
(2, 1, 2, 5000, '2025-02-01', 0),
(3, 1, 3, 5000, '2025-03-01', 0),
(4, 2, 1, 5000, '2025-01-01', 0),
(5, 2, 2, 5000, '2025-02-01', 0),
(6, 2, 3, 5000, '2025-03-01', 0),
(7, 2, 4, 5000, '2025-04-01', 0),
(8, 2, 5, 5000, '2025-05-01', 0),
(9, 2, 6, 5000, '2025-06-01', 0),
(10, 3, 1, 5000, '2025-01-01', 0),
(11, 3, 2, 5000, '2025-02-01', 0),
(12, 3, 3, 5000, '2025-03-01', 0),
(13, 3, 4, 5000, '2025-04-01', 0),
(14, 3, 5, 5000, '2025-05-01', 0),
(15, 3, 6, 5000, '2025-06-01', 0),
(16, 3, 7, 5000, '2025-07-01', 0),
(17, 3, 8, 5000, '2025-08-01', 0),
(18, 3, 9, 5000, '2025-09-01', 0),
(19, 3, 10, 5000, '2025-10-01', 0),
(20, 3, 11, 5000, '2025-11-01', 0),
(21, 3, 12, 5000, '2025-12-01', 0),
(22, 4, 1, 2000, '2025-01-01', 0),
(23, 4, 2, 2000, '2025-02-01', 0),
(24, 4, 3, 2000, '2025-03-01', 0),
(25, 4, 4, 2000, '2025-04-01', 0),
(26, 4, 5, 2000, '2025-05-01', 0),
(27, 4, 6, 2000, '2025-06-01', 0),
(28, 4, 7, 2000, '2025-07-01', 0),
(29, 4, 8, 2000, '2025-08-01', 0),
(30, 4, 9, 2000, '2025-09-01', 0),
(31, 5, 1, 3333, '2025-01-01', 0),
(32, 5, 2, 3333, '2025-02-01', 0),
(33, 5, 3, 3333, '2025-03-01', 0),
(34, 5, 4, 3333, '2025-04-01', 0),
(35, 5, 5, 3333, '2025-05-01', 0),
(36, 5, 6, 3333, '2025-06-01', 0),
(37, 5, 7, 3333, '2025-07-01', 0),
(38, 5, 8, 3333, '2025-08-01', 0),
(39, 5, 9, 3333, '2025-09-01', 0),
(40, 5, 10, 3333, '2025-10-01', 0),
(41, 5, 11, 3333, '2025-11-01', 0),
(42, 5, 12, 3333, '2025-12-01', 0);


-- Tabla Nacionalidades
INSERT INTO tpint_grupo_6_lab4.nacionalidades ( Pais) VALUES
( 'Argentina');

-- Tabla provincias
INSERT INTO tpint_grupo_6_lab4.provincias (IdNacionalidad, Provincia) VALUES
( 1, 'Buenos Aires'),
( 1, 'Santa Fe'),
( 1, 'Cordoba'),
( 1, 'Mendoza'),
( 1, 'Tucuman'),
( 1, 'Salta'),
( 1, 'Misiones'),
( 1, 'San Juan'),
( 1, 'San Luis');

-- Tabla localidades
INSERT INTO tpint_grupo_6_lab4.localidades ( Localiadad, IDProvincia) VALUES
('La plata', 1),
('Rosario', 2),
('Cordoba', 3),
('Mendoza', 4),
('Tucuman', 5),
('Salta', 6),
('Posadas', 7),
('San Juan', 8),
('San Luis', 9);
