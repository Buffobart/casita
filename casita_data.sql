-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.36-community - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for db_casita
DROP DATABASE IF EXISTS `db_casita`;
CREATE DATABASE IF NOT EXISTS `db_casita` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_casita`;


-- Dumping structure for table db_casita.categoria
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `IdCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.categoria: ~5 rows (approximately)
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`IdCategoria`, `Descripcion`) VALUES
	(1, 'PINTURA'),
	(2, 'MOLDES'),
	(3, 'HARDWARE'),
	(4, 'SOFTWARE'),
	(5, 'Nueva Categoria');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;


-- Dumping structure for table db_casita.cliente
DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `IdCliente` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Ruc` varchar(20) DEFAULT NULL,
  `Dni` varchar(20) DEFAULT NULL,
  `Direccion` varchar(50) DEFAULT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Obsv` text,
  PRIMARY KEY (`IdCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.cliente: ~2 rows (approximately)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`IdCliente`, `Nombre`, `Ruc`, `Dni`, `Direccion`, `Telefono`, `Obsv`) VALUES
	(1, 'CLIENTE 1', 'dfgt784512dfg', '4565123', 'dir 1', '789456123', 'Alta por Alan.'),
	(2, 'CLIENTE 2', 'ERTY345678UYT', 'ERRTT554R', 'DIRECCION 2', '34654321', 'CLIENTE DEMO 2');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;


-- Dumping structure for table db_casita.compra
DROP TABLE IF EXISTS `compra`;
CREATE TABLE IF NOT EXISTS `compra` (
  `IdCompra` int(11) NOT NULL AUTO_INCREMENT,
  `IdTipoDocumento` int(11) NOT NULL,
  `IdProveedor` int(11) NOT NULL,
  `IdEmpleado` int(11) NOT NULL,
  `Numero` varchar(20) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `SubTotal` decimal(8,2) DEFAULT NULL,
  `Igv` decimal(8,2) DEFAULT NULL,
  `Total` decimal(8,2) DEFAULT NULL,
  `Estado` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`IdCompra`),
  KEY `fk_Compra_Proveedor1_idx` (`IdProveedor`),
  KEY `fk_Compra_Empleado1_idx` (`IdEmpleado`),
  KEY `fk_Compra_TipoDocumento1_idx` (`IdTipoDocumento`),
  CONSTRAINT `fk_Compra_Empleado1` FOREIGN KEY (`IdEmpleado`) REFERENCES `empleado` (`IdEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_Proveedor1` FOREIGN KEY (`IdProveedor`) REFERENCES `proveedor` (`IdProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_TipoDocumento1` FOREIGN KEY (`IdTipoDocumento`) REFERENCES `tipodocumento` (`IdTipoDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.compra: ~9 rows (approximately)
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` (`IdCompra`, `IdTipoDocumento`, `IdProveedor`, `IdEmpleado`, `Numero`, `Fecha`, `SubTotal`, `Igv`, `Total`, `Estado`) VALUES
	(1, 1, 1, 2, 'C00001', '2015-02-06', 67.80, 12.20, 80.00, 'NORMAL'),
	(2, 1, 1, 2, 'C00002', '2015-02-06', 101.69, 18.30, 120.00, 'NORMAL'),
	(3, 1, 1, 2, 'C00003', '2015-02-26', 101.69, 18.30, 120.00, 'NORMAL'),
	(4, 2, 2, 2, 'C00004', '2015-02-27', 1954.95, 351.89, 2306.84, 'NORMAL'),
	(5, 1, 2, 2, 'C00005', '2015-02-28', 1248.17, 224.67, 1472.84, 'NORMAL'),
	(6, 1, 2, 2, 'C00006', '2015-03-01', 1726.98, 310.86, 2037.84, 'NORMAL'),
	(7, 1, 2, 2, 'C00007', '2015-03-01', 6.78, 1.22, 8.00, 'NORMAL'),
	(8, 1, 2, 2, 'C00008', '2015-03-01', 6.78, 1.22, 8.00, 'NORMAL'),
	(9, 1, 1, 2, 'C00009', '2015-03-02', 439.70, 70.35, 510.05, 'NORMAL');
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;


-- Dumping structure for table db_casita.cuenta
DROP TABLE IF EXISTS `cuenta`;
CREATE TABLE IF NOT EXISTS `cuenta` (
  `IdCuenta` int(10) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` text,
  `CuentaDefault` bit(1) DEFAULT NULL,
  `Balance` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`IdCuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.cuenta: ~2 rows (approximately)
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` (`IdCuenta`, `Nombre`, `Descripcion`, `CuentaDefault`, `Balance`) VALUES
	(1, 'BANCARIA', 'Cuenta de Banco', b'00000000', 150000.00),
	(2, 'CAJA', 'Caja Chica', b'10000000', 1204.00);
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;


-- Dumping structure for table db_casita.detallecompra
DROP TABLE IF EXISTS `detallecompra`;
CREATE TABLE IF NOT EXISTS `detallecompra` (
  `idDetalleCompra` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `IdCompra` int(11) NOT NULL,
  `IdProducto` int(11) NOT NULL,
  `Cantidad` decimal(8,2) NOT NULL,
  `Precio` decimal(8,2) NOT NULL,
  `Total` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idDetalleCompra`),
  KEY `fk_DetalleCompra_Compra1_idx` (`IdCompra`),
  KEY `fk_DetalleCompra_Producto1_idx` (`IdProducto`),
  CONSTRAINT `fk_DetalleCompra_Compra1` FOREIGN KEY (`IdCompra`) REFERENCES `compra` (`IdCompra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DetalleCompra_Producto1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`IdProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.detallecompra: ~16 rows (approximately)
/*!40000 ALTER TABLE `detallecompra` DISABLE KEYS */;
INSERT INTO `detallecompra` (`idDetalleCompra`, `IdCompra`, `IdProducto`, `Cantidad`, `Precio`, `Total`) VALUES
	(1, 1, 1, 1.00, 80.00, 80.00),
	(2, 2, 1, 1.00, 120.00, 120.00),
	(3, 3, 1, 1.00, 120.00, 120.00),
	(4, 4, 4, 2.00, 245.00, 490.00),
	(5, 4, 5, 1.00, 1456.84, 1456.84),
	(6, 4, 1, 3.00, 120.00, 360.00),
	(7, 5, 6, 2.00, 8.00, 16.00),
	(8, 5, 5, 1.00, 1456.84, 1456.84),
	(9, 6, 1, 1.00, 120.00, 120.00),
	(10, 6, 6, 2.00, 8.00, 16.00),
	(11, 6, 5, 1.00, 1456.84, 1456.84),
	(12, 6, 3, 2.00, 100.00, 200.00),
	(13, 6, 4, 1.00, 245.00, 245.00),
	(14, 7, 6, 1.00, 8.00, 8.00),
	(15, 8, 6, 1.00, 8.00, 8.00),
	(16, 9, 7, 1.00, 510.05, 510.05);
/*!40000 ALTER TABLE `detallecompra` ENABLE KEYS */;


-- Dumping structure for table db_casita.detalleventa
DROP TABLE IF EXISTS `detalleventa`;
CREATE TABLE IF NOT EXISTS `detalleventa` (
  `idDetalleVenta` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `IdVenta` int(11) NOT NULL,
  `IdProducto` int(11) NOT NULL,
  `Cantidad` decimal(8,2) NOT NULL,
  `Costo` decimal(8,2) NOT NULL,
  `Precio` decimal(8,2) NOT NULL,
  `Total` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idDetalleVenta`),
  KEY `fk_DetalleVenta_Producto1_idx` (`IdProducto`),
  KEY `fk_DetalleVenta_Venta1_idx` (`IdVenta`),
  CONSTRAINT `fk_DetalleVenta_Producto1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`IdProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DetalleVenta_Venta1` FOREIGN KEY (`IdVenta`) REFERENCES `venta` (`IdVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.detalleventa: ~17 rows (approximately)
/*!40000 ALTER TABLE `detalleventa` DISABLE KEYS */;
INSERT INTO `detalleventa` (`idDetalleVenta`, `IdVenta`, `IdProducto`, `Cantidad`, `Costo`, `Precio`, `Total`) VALUES
	(1, 1, 1, 1.00, 120.00, 140.00, 140.00),
	(2, 2, 1, 1.00, 120.00, 140.00, 140.00),
	(3, 3, 4, 1.00, 245.00, 310.00, 303.80),
	(4, 3, 1, 1.00, 120.00, 150.00, 150.00),
	(5, 4, 3, 4.00, 100.00, 150.00, 585.00),
	(6, 5, 4, 3.00, 245.00, 310.00, 750.20),
	(7, 5, 1, 1.00, 120.00, 150.00, 150.00),
	(8, 10, 3, 1.00, 100.00, 150.00, 150.00),
	(9, 11, 1, 1.00, 120.00, 150.00, 150.00),
	(10, 12, 1, 1.00, 120.00, 150.00, 150.00),
	(11, 13, 4, 1.00, 245.00, 310.00, 310.00),
	(12, 14, 4, 1.00, 245.00, 310.00, 310.00),
	(13, 15, 1, 1.00, 120.00, 150.00, 150.00),
	(14, 16, 5, 2.00, 1456.84, 2543.45, 5086.90),
	(15, 16, 1, 1.00, 120.00, 150.00, 150.00),
	(16, 17, 4, 1.00, 245.00, 310.00, 310.00),
	(17, 18, 6, 1.00, 8.00, 10.00, 10.00);
/*!40000 ALTER TABLE `detalleventa` ENABLE KEYS */;


-- Dumping structure for table db_casita.empleado
DROP TABLE IF EXISTS `empleado`;
CREATE TABLE IF NOT EXISTS `empleado` (
  `IdEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Apellido` varchar(80) NOT NULL,
  `Sexo` varchar(1) NOT NULL,
  `FechaNac` date NOT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `Telefono` varchar(10) DEFAULT NULL,
  `Celular` varchar(15) DEFAULT NULL,
  `Email` varchar(80) DEFAULT NULL,
  `Dni` varchar(8) DEFAULT NULL,
  `FechaIng` date NOT NULL,
  `Sueldo` decimal(8,2) DEFAULT NULL,
  `Estado` varchar(30) NOT NULL,
  `Usuario` varchar(20) DEFAULT NULL,
  `Contrasena` text,
  `IdTipoUsuario` int(11) NOT NULL,
  PRIMARY KEY (`IdEmpleado`),
  KEY `fk_Empleado_TipoUsuario1_idx` (`IdTipoUsuario`),
  CONSTRAINT `fk_Empleado_TipoUsuario1` FOREIGN KEY (`IdTipoUsuario`) REFERENCES `tipousuario` (`IdTipoUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.empleado: ~5 rows (approximately)
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` (`IdEmpleado`, `Nombre`, `Apellido`, `Sexo`, `FechaNac`, `Direccion`, `Telefono`, `Celular`, `Email`, `Dni`, `FechaIng`, `Sueldo`, `Estado`, `Usuario`, `Contrasena`, `IdTipoUsuario`) VALUES
	(1, 'Edgar', 'Cotrado Flores', 'M', '2013-06-15', 'Para Grande', '315199', '9526572', 'asd@gmail.com', '45736020', '2013-06-15', 750.00, 'ACTIVO', 'edgar', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 1),
	(2, 'Alan', 'Diaz', 'M', '2015-01-18', 'Zafiro', '332564', '698525', 'alan@mail.com', '78978978', '2015-01-18', 700.00, 'ACTIVO', 'alan', 'd404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db', 1),
	(3, 'sinc', 'd', 'M', '2015-02-06', '', '', '', '', '1236654', '2015-02-06', 0.00, 'ACTIVO', 'sinc', '5aadb45520dcd8726b2822a7a78bb53d794f557199d5d4abdedd2c55a4bd6ca73607605c558de3db80c8e86c3196484566163ed1327e82e8b6757d1932113cb8', 3),
	(4, 'soloc', 'd', 'M', '2015-02-06', '', '', '', '', '654789', '2015-02-06', 0.00, 'ACTIVO', 'soloc', '5aadb45520dcd8726b2822a7a78bb53d794f557199d5d4abdedd2c55a4bd6ca73607605c558de3db80c8e86c3196484566163ed1327e82e8b6757d1932113cb8', 4),
	(5, 'Descuentos', 'd', 'M', '2015-02-06', '', '', '', '', '1236589', '2015-02-06', 0.00, 'ACTIVO', 'desc', 'd404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db', 5);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;


-- Dumping structure for table db_casita.moneda
DROP TABLE IF EXISTS `moneda`;
CREATE TABLE IF NOT EXISTS `moneda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `tipoCambio` decimal(8,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.moneda: ~2 rows (approximately)
/*!40000 ALTER TABLE `moneda` DISABLE KEYS */;
INSERT INTO `moneda` (`id`, `nombre`, `tipoCambio`) VALUES
	(1, 'Peso (MXN)', 1.0000),
	(2, 'Dolar (USD)', 15.0016);
/*!40000 ALTER TABLE `moneda` ENABLE KEYS */;


-- Dumping structure for table db_casita.operacion
DROP TABLE IF EXISTS `operacion`;
CREATE TABLE IF NOT EXISTS `operacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) DEFAULT NULL,
  `cuenta` int(11) DEFAULT NULL,
  `cantidad` decimal(8,2) DEFAULT NULL,
  `montoInicial` decimal(8,2) DEFAULT NULL,
  `montoFinal` decimal(8,2) DEFAULT NULL,
  `usuario` int(11) DEFAULT NULL,
  `hora` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_idx` (`usuario`),
  KEY `fk_cuenta_idx` (`cuenta`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario`) REFERENCES `empleado` (`IdEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuenta` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`IdCuenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.operacion: ~3 rows (approximately)
/*!40000 ALTER TABLE `operacion` DISABLE KEYS */;
INSERT INTO `operacion` (`id`, `tipo`, `cuenta`, `cantidad`, `montoInicial`, `montoFinal`, `usuario`, `hora`) VALUES
	(1, 'VENTA', 2, 30.00, 17632.33, 9.00, 2, '2015-03-02 23:09:55'),
	(2, 'VENTA', 2, 30.00, 17632.33, 17602.33, 2, '2015-03-02 23:10:55'),
	(4, 'VENTA', 2, 30.00, 1234.00, 1204.00, 2, '2015-03-02 23:29:23');
/*!40000 ALTER TABLE `operacion` ENABLE KEYS */;


-- Dumping structure for table db_casita.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `IdProducto` int(11) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(50) DEFAULT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` text,
  `Stock` decimal(8,2) DEFAULT NULL,
  `StockMin` decimal(8,2) DEFAULT NULL,
  `PrecioCosto` decimal(8,2) DEFAULT NULL,
  `PrecioVenta` decimal(8,2) DEFAULT NULL,
  `PrecioVenta2` decimal(8,2) DEFAULT NULL,
  `Utilidad` decimal(8,2) DEFAULT NULL,
  `Estado` varchar(30) NOT NULL,
  `IdCategoria` int(11) NOT NULL,
  `DescuentoProveedor` float unsigned NOT NULL,
  `DescuentoVenta` float unsigned NOT NULL,
  `Moneda` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdProducto`),
  KEY `fk_Producto_Categoria_idx` (`IdCategoria`),
  KEY `fk_Moneda_idx` (`Moneda`),
  CONSTRAINT `fk_Moneda` FOREIGN KEY (`Moneda`) REFERENCES `moneda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_Categoria` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.producto: ~7 rows (approximately)
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`IdProducto`, `Codigo`, `Nombre`, `Descripcion`, `Stock`, `StockMin`, `PrecioCosto`, `PrecioVenta`, `PrecioVenta2`, `Utilidad`, `Estado`, `IdCategoria`, `DescuentoProveedor`, `DescuentoVenta`, `Moneda`) VALUES
	(1, '123665', 'Raton Lazer', 'Raton Lazer Color Negro', 12.00, 1.00, 120.00, 150.00, 140.00, 30.00, 'ACTIVO', 3, 0, 0, 1),
	(2, '789654', 'Demo P1', 'P1', 2.00, 1.00, 123.00, 145.00, NULL, 22.00, 'INACTIVO', 3, 0, 0, 1),
	(3, '104604', 'Telefono Inalambrico', 'Telefono inalambrico desc', 9.00, 1.00, 100.00, 150.00, NULL, 50.00, 'ACTIVO', 3, 0, 0, 1),
	(4, '293112', 'Teclado Inalambrico', 'Logitech', 4.00, 1.00, 245.00, 310.00, NULL, 82.15, 'ACTIVO', 3, 7, 5, 1),
	(5, '186282', 'Monitor LCD 19"', 'Monitor Dell', 7.00, 1.00, 1456.84, 2543.45, NULL, 1232.29, 'ACTIVO', 3, 10, 5, 1),
	(6, '7501086801046', 'Agua Enbotellada E-Pura 1L', '', 16.00, 1.00, 8.00, 10.00, NULL, 2.08, 'ACTIVO', 5, 1, 1, 1),
	(7, '9783191816902', 'Themen 1 Aktuell ', 'Kursbuch + Arbeitsbuch', 11.00, 1.00, 34.00, 56.00, NULL, 22.00, 'ACTIVO', 5, 0, 0, 2);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;


-- Dumping structure for table db_casita.proveedor
DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE IF NOT EXISTS `proveedor` (
  `IdProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Ruc` varchar(20) DEFAULT NULL,
  `Dni` varchar(20) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `Telefono` varchar(10) DEFAULT NULL,
  `Celular` varchar(15) DEFAULT NULL,
  `Email` varchar(80) DEFAULT NULL,
  `Cuenta1` varchar(50) DEFAULT NULL,
  `Cuenta2` varchar(50) DEFAULT NULL,
  `Estado` varchar(30) NOT NULL,
  `Obsv` text,
  PRIMARY KEY (`IdProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.proveedor: ~2 rows (approximately)
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` (`IdProveedor`, `Nombre`, `Ruc`, `Dni`, `Direccion`, `Telefono`, `Celular`, `Email`, `Cuenta1`, `Cuenta2`, `Estado`, `Obsv`) VALUES
	(1, 'SIN PROVEEDOR', '', '', '', '', '', '', '', '', 'ACTIVO', ''),
	(2, 'SURTIDOR COMPONENTES DE HARDWARE', 'SCHR234567FRT', '234ERGF43', 'Av. López Cotilla 1043, Guadalajara', '3334565432', '3332345432', 'ventas@schr.com', '432234567', '5467857697860798', 'ACTIVO', 'Observaciones del Proveedor');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;


-- Dumping structure for procedure db_casita.SP_I_Categoria
DROP PROCEDURE IF EXISTS `SP_I_Categoria`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Categoria`(	
			pdescripcion varchar(100)
		)
BEGIN		
		INSERT INTO categoria(descripcion)
		VALUES(pdescripcion);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Cliente
DROP PROCEDURE IF EXISTS `SP_I_Cliente`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Cliente`(	
			pnombre varchar(100),
			pruc varchar(20),
			pdni varchar(20),
			pdireccion varchar(50),
			ptelefono varchar(15),
			pobsv text
		)
BEGIN		
		INSERT INTO cliente(nombre,ruc,dni,direccion,telefono,obsv)
		VALUES(pnombre,pruc,pdni,pdireccion,ptelefono,pobsv);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Compra
DROP PROCEDURE IF EXISTS `SP_I_Compra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Compra`(	
			pidtipodocumento int,
			pidproveedor int,
			pidempleado int,
			pnumero varchar(20),
			pfecha date,
			psubtotal decimal(8,2),
			pigv decimal(8,2),
			ptotal decimal(8,2),
			pestado varchar(30)
		)
BEGIN		
		INSERT INTO compra(idtipodocumento,idproveedor,idempleado,numero,fecha,subtotal,igv,total,estado)
		VALUES(pidtipodocumento,pidproveedor,pidempleado,pnumero,pfecha,psubtotal,pigv,ptotal,pestado);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Cuenta
DROP PROCEDURE IF EXISTS `SP_I_Cuenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Cuenta`(	
			pid int,
			pnombre varchar(100),
			pdescrpcion text,
			pcuentadefault bit,
			pbalance decimal
		)
BEGIN
	IF pcuentadefault = 1 THEN
		UPDATE cuenta SET
		CuentaDefault = 0;
	END IF;

	INSERT INTO cuenta(idcuenta, nombre, descripcion, cuentadefault, balance)
	VALUES(pid, pnombre, pdescrpcion, pcuentadefault, pbalance);

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_DetalleCompra
DROP PROCEDURE IF EXISTS `SP_I_DetalleCompra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_DetalleCompra`(	
			pidcompra int,
			pidproducto int,
			pcantidad decimal(8,2),
			pprecio decimal(8,2),
			ptotal decimal(8,2)
		)
BEGIN		
		INSERT INTO detallecompra(idcompra,idproducto,cantidad,precio,total)
		VALUES(pidcompra,pidproducto,pcantidad,pprecio,ptotal);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_DetalleVenta
DROP PROCEDURE IF EXISTS `SP_I_DetalleVenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_DetalleVenta`(	
			pidventa int,
			pidproducto int,
			pcantidad decimal(8,2),
			pcosto decimal(8,2),
			pprecio decimal(8,2),
			ptotal decimal(8,2)
		)
BEGIN		
		INSERT INTO detalleventa(idventa,idproducto,cantidad,costo,precio,total)
		VALUES(pidventa,pidproducto,pcantidad,pcosto,pprecio,ptotal);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Empleado
DROP PROCEDURE IF EXISTS `SP_I_Empleado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Empleado`(	
			pnombre varchar(50),
			papellido varchar(80),
			psexo varchar(1),
			pfechanac date,
			pdireccion varchar(100),
			ptelefono varchar(10),
			pcelular varchar(15),
			pemail varchar(80),
			pdni varchar(8),
			pfechaing date,
			psueldo decimal(8,2),
			pestado varchar(30),
			pusuario varchar(20),
			pcontrasena text,
			pidtipousuario int
		)
BEGIN		
		INSERT INTO empleado(nombre,apellido,sexo,fechanac,direccion,telefono,celular,email,dni,fechaing,sueldo,estado,usuario,contrasena,idtipousuario)
		VALUES(pnombre,papellido,psexo,pfechanac,pdireccion,ptelefono,pcelular,pemail,pdni,pfechaing,psueldo,pestado,pusuario,pcontrasena,pidtipousuario);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Producto
DROP PROCEDURE IF EXISTS `SP_I_Producto`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Producto`(	
			pcodigo varchar(50),
			pnombre varchar(100),
			pdescripcion text,
			pstock decimal(8,2),
			pstockmin decimal(8,2),
			ppreciocosto decimal(8,2),
			pprecioventa decimal(8,2),
			pprecioventa2 decimal(8,2),
			putilidad decimal(8,2),
			pestado varchar(30),
			pidcategoria int
		)
BEGIN		
		INSERT INTO producto(codigo,nombre,descripcion,stock,stockmin,preciocosto,precioventa,precioventa2,utilidad,estado,idcategoria)
		VALUES(pcodigo,pnombre,pdescripcion,pstock,pstockmin,ppreciocosto,pprecioventa,pprecioventa2,putilidad,pestado,pidcategoria);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Proveedor
DROP PROCEDURE IF EXISTS `SP_I_Proveedor`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Proveedor`(IN `pnombre` varchar(100), IN `pruc` varchar(20), IN `pdni` varchar(20), IN `pdireccion` varchar(100), IN `ptelefono` varchar(10), IN `pcelular` varchar(15), IN `pemail` varchar(80), IN `pcuenta1` varchar(50), IN `pcuenta2` varchar(50), IN `pestado` varchar(30), IN `pobsv` text
		)
BEGIN		
		INSERT INTO proveedor(nombre,ruc,dni,direccion,telefono,celular,email,cuenta1,cuenta2,estado,obsv)
		VALUES(pnombre,pruc,pdni,pdireccion,ptelefono,pcelular,pemail,pcuenta1,pcuenta2,pestado,pobsv);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_TipoDocumento
DROP PROCEDURE IF EXISTS `SP_I_TipoDocumento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_TipoDocumento`(	
			pdescripcion varchar(80)
		)
BEGIN		
		INSERT INTO tipodocumento(descripcion)
		VALUES(pdescripcion);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_TipoUsuario
DROP PROCEDURE IF EXISTS `SP_I_TipoUsuario`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_TipoUsuario`(	
			pdescripcion varchar(80),
			pp_venta int,
			pp_compra int,
			pp_producto int,
			pp_proveedor int,
			pp_empleado int,
			pp_cliente int,
			pp_categoria int,
			pp_tipodoc int,
			pp_tipouser int,
			pp_anularv int,
			pp_anularc int,
			pp_estadoprod int,
			pp_ventare int,
			pp_ventade int,
			pp_estadistica int,
			pp_comprare int,
			pp_comprade int,
			pp_pass int,
			pp_respaldar int,
			pp_restaurar int,
			pp_caja int,
			pp_cuentas int
		)
BEGIN		
		INSERT INTO tipousuario(descripcion,p_venta,p_compra,p_producto,p_proveedor,p_empleado,p_cliente,p_categoria,p_tipodoc,p_tipouser,p_anularv,p_anularc,
		p_estadoprod,p_ventare,p_ventade,p_estadistica,p_comprare,p_comprade,p_pass,p_respaldar,p_restaurar,p_caja, p_cuentas)
		VALUES(pdescripcion,pp_venta,pp_compra,pp_producto,pp_proveedor,pp_empleado,pp_cliente,pp_categoria,pp_tipodoc,pp_tipouser,pp_anularv,pp_anularc,
		pp_estadoprod,pp_ventare,pp_ventade,pp_estadistica,pp_comprare,pp_comprade,pp_pass,pp_respaldar,pp_restaurar,pp_caja, pp_cuentas);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_I_Venta
DROP PROCEDURE IF EXISTS `SP_I_Venta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Venta`(	
			pidtipodocumento int,
			pidcliente int,
			pidempleado int,
			pserie varchar(5),
			pnumero varchar(20),
			pfecha date,
			ptotalventa decimal(8,2),
			pdescuento decimal(8,2),
			psubtotal decimal(8,2),
			pigv decimal(8,2),
			ptotalpagar decimal(8,2),
			pestado varchar(30)
		)
BEGIN		
		INSERT INTO venta(idtipodocumento,idcliente,idempleado,serie,numero,fecha,totalventa,descuento,subtotal,igv,totalpagar,estado)
		VALUES(pidtipodocumento,pidcliente,pidempleado,pserie,pnumero,pfecha,ptotalventa,pdescuento,psubtotal,pigv,ptotalpagar,pestado);
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Categoria
DROP PROCEDURE IF EXISTS `SP_S_Categoria`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Categoria`(	
		
		)
BEGIN
		SELECT * FROM categoria;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_CategoriaPorParametro
DROP PROCEDURE IF EXISTS `SP_S_CategoriaPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_CategoriaPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT c.IdCategoria,c.Descripcion FROM categoria AS c WHERE c.IdCategoria=pbusqueda;
	ELSEIF pcriterio = "descripcion" THEN
		SELECT c.IdCategoria,c.Descripcion FROM categoria AS c WHERE c.Descripcion LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT c.IdCategoria,c.Descripcion FROM categoria AS c;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Cliente
DROP PROCEDURE IF EXISTS `SP_S_Cliente`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Cliente`(
	
	)
BEGIN
		SELECT * FROM cliente;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ClientePorParametro
DROP PROCEDURE IF EXISTS `SP_S_ClientePorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ClientePorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT c.IdCliente,c.Nombre,c.Ruc,c.Dni,c.Direccion,c.Telefono,c.Obsv FROM cliente AS c WHERE c.IdCliente=pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT c.IdCliente,c.Nombre,c.Ruc,c.Dni,c.Direccion,c.Telefono,c.Obsv FROM cliente AS c WHERE c.Nombre LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "ruc" THEN
		SELECT c.IdCliente,c.Nombre,c.Ruc,c.Dni,c.Direccion,c.Telefono,c.Obsv FROM cliente AS c WHERE c.Ruc LIKE CONCAT("%",pbusqueda,"%");
   ELSEIF pcriterio = "dni" THEN
		SELECT c.IdCliente,c.Nombre,c.Ruc,c.Dni,c.Direccion,c.Telefono,c.Obsv FROM cliente AS c WHERE c.Dni LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT c.IdCliente,c.Nombre,c.Ruc,c.Dni,c.Direccion,c.Telefono,c.Obsv FROM cliente AS c;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Compra
DROP PROCEDURE IF EXISTS `SP_S_Compra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Compra`(
	
	)
BEGIN
		SELECT c.IdCompra,td.Descripcion AS TipoDocumento,p.Nombre AS Proveedor,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,c.Numero,c.Fecha,c.SubTotal,c.Igv,c.Total,c.Estado
		FROM compra AS c
		INNER JOIN tipodocumento AS td ON c.IdTipoDocumento=td.IdTipoDocumento	 
		INNER JOIN proveedor AS p ON c.IdProveedor=p.IdProveedor		
		INNER JOIN empleado AS e ON c.IdEmpleado=e.IdEmpleado
		ORDER BY c.IdCompra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_CompraPorDetalle
DROP PROCEDURE IF EXISTS `SP_S_CompraPorDetalle`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_CompraPorDetalle`(
			pcriterio varchar(30),
			pfechaini date,
			pfechafin date
	)
BEGIN
		IF pcriterio = "consultar" THEN
			SELECT p.Codigo,p.Nombre AS Producto,ca.Descripcion AS Categoria,dc.Precio,
			SUM(dc.Cantidad) AS Cantidad,SUM(dc.Total) AS Total FROM compra AS c
			INNER JOIN detallecompra AS dc ON c.IdCompra=dc.IdCompra
			INNER JOIN producto AS p ON dc.IdProducto=p.IdProducto
			INNER JOIN categoria AS ca ON p.IdCategoria=ca.IdCategoria
			WHERE (c.Fecha>=pfechaini AND c.Fecha<=pfechafin) AND c.Estado="NORMAL" GROUP BY p.IdProducto
			ORDER BY c.IdCompra DESC;
		END IF;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_CompraPorFecha
DROP PROCEDURE IF EXISTS `SP_S_CompraPorFecha`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_CompraPorFecha`(
			pcriterio varchar(30),
			pfechaini date,
			pfechafin date,
			pdocumento varchar(30)
	)
BEGIN
		IF pcriterio = "anular" THEN
			SELECT c.IdCompra,p.Nombre AS Proveedor,c.Fecha,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,td.Descripcion AS TipoDocumento,c.Numero,
			c.Estado,c.Total FROM compra AS c
			INNER JOIN tipodocumento AS td ON c.IdTipoDocumento=td.IdTipoDocumento
			INNER JOIN proveedor AS p ON c.IdProveedor=p.IdProveedor
			INNER JOIN empleado AS e ON c.IdEmpleado=e.IdEmpleado
			WHERE (c.Fecha>=pfechaini AND c.Fecha<=pfechafin) AND td.Descripcion=pdocumento ORDER BY c.IdCompra DESC;
		ELSEIF pcriterio = "consultar" THEN
		   SELECT c.IdCompra,p.Nombre AS Proveedor,c.Fecha,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,td.Descripcion AS TipoDocumento,c.Numero,
			c.Estado,c.Total FROM compra AS c
			INNER JOIN tipodocumento AS td ON c.IdTipoDocumento=td.IdTipoDocumento
			INNER JOIN proveedor AS p ON c.IdProveedor=p.IdProveedor
			INNER JOIN empleado AS e ON c.IdEmpleado=e.IdEmpleado
			WHERE c.Fecha>=pfechaini AND c.Fecha<=pfechafin ORDER BY c.IdCompra DESC;
		END IF;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_CompraPorID
DROP PROCEDURE IF EXISTS `SP_S_CompraPorID`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_CompraPorID`(IN `pidcompra` int
	)
BEGIN
		SELECT c.IdCompra,c.Fecha,c.Numero,c.Estado,c.Total,p.Nombre,c.Igv,c.SubTotal AS Proveedor, p.Ruc AS Ruc, p.IdProveedor AS IdProveedor, p.Telefono AS Telefono, p.Direccion AS Direccion
		FROM compra AS c
		INNER JOIN proveedor AS p ON c.IdProveedor=p.IdProveedor
		WHERE c.IdCompra = pidcompra ORDER BY c.IdCompra DESC;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_DetalleCompra
DROP PROCEDURE IF EXISTS `SP_S_DetalleCompra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_DetalleCompra`(
	
	)
BEGIN
		SELECT * FROM detallecompra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_DetalleCompraPorParametro
DROP PROCEDURE IF EXISTS `SP_S_DetalleCompraPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_DetalleCompraPorParametro`(
		   pcriterio varchar(20),
			pbusqueda varchar(20)
	)
BEGIN
			IF pcriterio = "id" THEN
				SELECT dc.IdCompra,p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,dc.Cantidad,dc.Precio,dc.Total  FROM detallecompra AS dc
				INNER JOIN producto AS p ON dc.IdProducto=p.IdProducto
				WHERE dc.IdCompra=pbusqueda ORDER BY dc.IdCompra;
			
			END IF; 
			
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_DetalleVenta
DROP PROCEDURE IF EXISTS `SP_S_DetalleVenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_DetalleVenta`(
	
	)
BEGIN
		SELECT * FROM detalleventa;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_DetalleVentaPorParametro
DROP PROCEDURE IF EXISTS `SP_S_DetalleVentaPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_DetalleVentaPorParametro`(
		   pcriterio varchar(20),
			pbusqueda varchar(20)
	)
BEGIN
			IF pcriterio = "id" THEN
				SELECT dv.IdVenta,p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,dv.Cantidad,dv.Precio,dv.Total  FROM detalleventa AS dv
				INNER JOIN producto AS p ON dv.IdProducto=p.IdProducto
				WHERE dv.IdVenta=pbusqueda ORDER BY dv.IdVenta;
			
			END IF; 
			
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Empleado
DROP PROCEDURE IF EXISTS `SP_S_Empleado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Empleado`(
	
	)
BEGIN
		SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion AS TipoUsuario
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario=tu.IdTipoUsuario
		ORDER BY e.IdEmpleado;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_EmpleadoPorParametro
DROP PROCEDURE IF EXISTS `SP_S_EmpleadoPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_EmpleadoPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion AS TipoUsuario
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario = tu.IdTipoUsuario 
		WHERE e.IdEmpleado=pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion AS TipoUsuario
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario = tu.IdTipoUsuario 
		WHERE ((e.Nombre LIKE CONCAT("%",pbusqueda,"%")) OR (e.Apellido LIKE CONCAT("%",pbusqueda,"%")));
	ELSEIF pcriterio = "dni" THEN
		SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion AS TipoUsuario
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario = tu.IdTipoUsuario 
		WHERE e.Dni LIKE CONCAT("%",pbusqueda,"%");
	ELSE
	   SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion AS TipoUsuario
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario = tu.IdTipoUsuario;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Login
DROP PROCEDURE IF EXISTS `SP_S_Login`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Login`(	
			pusuario varchar(20),
			pcontrasena text,
			pdescripcion varchar(80)
		)
BEGIN
	
		SELECT e.IdEmpleado,e.Nombre,e.Apellido,e.Sexo,e.FechaNac,e.Direccion,e.Telefono,e.Celular,e.Email,
		e.Dni,e.FechaIng,e.Sueldo,e.Estado,e.Usuario,e.`Contrasena`,tu.Descripcion
		FROM empleado AS e INNER JOIN tipousuario AS tu ON e.IdTipoUsuario = tu.IdTipoUsuario WHERE e.Usuario = pusuario AND e.`Contrasena` = pcontrasena AND tu.Descripcion=pdescripcion;
		
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_LoginPermisos
DROP PROCEDURE IF EXISTS `SP_S_LoginPermisos`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_LoginPermisos`(IN `pnombre_usuario` varchar(20), IN `pdescripcion_tipousuario` varchar(80)

		)
BEGIN
	
		SELECT tu.IdTipoUsuario,e.Usuario,tu.Descripcion,tu.p_venta,tu.p_compra,tu.p_producto,tu.p_proveedor,tu.p_empleado,tu.p_cliente,tu.p_categoria,tu.p_tipodoc,tu.p_tipouser,
		tu.p_anularv,tu.p_anularc,tu.p_estadoprod,tu.p_ventare,tu.p_ventade,tu.p_estadistica,tu.p_comprare,tu.p_comprade,tu.p_pass,tu.p_respaldar,tu.p_restaurar,tu.p_caja,tu.p_cuentas,tu.p_descuento
		FROM tipousuario AS tu INNER JOIN empleado AS e ON tu.IdTipoUsuario = e.IdTipoUsuario WHERE e.Usuario = pnombre_usuario AND tu.Descripcion=pdescripcion_tipousuario;
		
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Producto
DROP PROCEDURE IF EXISTS `SP_S_Producto`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Producto`()
BEGIN
		SELECT p.*,c.Descripcion AS categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria=c.IdCategoria
		ORDER BY p.IdProducto;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ProductoActivo
DROP PROCEDURE IF EXISTS `SP_S_ProductoActivo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ProductoActivo`(
	
	)
BEGIN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria=c.IdCategoria WHERE p.Estado="Activo"
		ORDER BY p.IdProducto;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ProductoActivoPorParametro
DROP PROCEDURE IF EXISTS `SP_S_ProductoActivoPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ProductoActivoPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(50)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.IdProducto=pbusqueda AND p.Estado="Activo";
 	ELSEIF pcriterio = "codigo" THEN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Codigo=pbusqueda AND p.Estado="Activo";
	ELSEIF pcriterio = "nombre" THEN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Nombre LIKE CONCAT("%",pbusqueda,"%") AND p.Estado="Activo";
	ELSEIF pcriterio = "descripcion" THEN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Descripcion LIKE CONCAT("%",pbusqueda,"%") AND p.Estado="Activo";
	ELSEIF pcriterio = "categoria" THEN
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE c.Descripcion LIKE CONCAT("%",pbusqueda,"%") AND p.Estado="Activo";
	ELSE
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.PrecioVenta2,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria WHERE p.Estado="Activo";
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ProductoPorParametro
DROP PROCEDURE IF EXISTS `SP_S_ProductoPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ProductoPorParametro`(IN `pcriterio` varchar(20), IN `pbusqueda` varchar(50)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.IdProducto=pbusqueda;
	ELSEIF pcriterio = "codigo" THEN
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Codigo=pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Nombre LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "descripcion" THEN
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Descripcion LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "categoria" THEN
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE c.Descripcion LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT p.*,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ProductoVerificarCodigoBar
DROP PROCEDURE IF EXISTS `SP_S_ProductoVerificarCodigoBar`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ProductoVerificarCodigoBar`(	
			pbusqueda varchar(50)
		)
BEGIN
	
		SELECT p.IdProducto,p.Codigo,p.Nombre,p.Descripcion,p.Stock,p.StockMin,p.PrecioCosto,p.PrecioVenta,p.Utilidad,p.Estado,c.Descripcion AS Categoria
		FROM producto AS p INNER JOIN categoria AS c ON p.IdCategoria = c.IdCategoria
		WHERE p.Codigo=pbusqueda;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Proveedor
DROP PROCEDURE IF EXISTS `SP_S_Proveedor`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Proveedor`(
	
	)
BEGIN
		SELECT * FROM proveedor;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_ProveedorPorParametro
DROP PROCEDURE IF EXISTS `SP_S_ProveedorPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ProveedorPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT p.IdProveedor,p.Nombre,p.Ruc,p.Dni,p.Direccion,p.Telefono,p.Celular,p.Email,p.Cuenta1,p.Cuenta2,p.Estado,p.Obsv FROM proveedor AS p WHERE p.IdProveedor=pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT p.IdProveedor,p.Nombre,p.Ruc,p.Dni,p.Direccion,p.Telefono,p.Celular,p.Email,p.Cuenta1,p.Cuenta2,p.Estado,p.Obsv FROM proveedor AS p WHERE p.Nombre LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "ruc" THEN
		SELECT p.IdProveedor,p.Nombre,p.Ruc,p.Dni,p.Direccion,p.Telefono,p.Celular,p.Email,p.Cuenta1,p.Cuenta2,p.Estado,p.Obsv FROM proveedor AS p WHERE p.Ruc LIKE CONCAT("%",pbusqueda,"%");
   ELSEIF pcriterio = "dni" THEN
		SELECT p.IdProveedor,p.Nombre,p.Ruc,p.Dni,p.Direccion,p.Telefono,p.Celular,p.Email,p.Cuenta1,p.Cuenta2,p.Estado,p.Obsv FROM proveedor AS p WHERE p.Dni LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT p.IdProveedor,p.Nombre,p.Ruc,p.Dni,p.Direccion,p.Telefono,p.Celular,p.Email,p.Cuenta1,p.Cuenta2,p.Estado,p.Obsv FROM proveedor AS p;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_TipoDocumento
DROP PROCEDURE IF EXISTS `SP_S_TipoDocumento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_TipoDocumento`(	
		
		)
BEGIN
		SELECT * FROM tipodocumento;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_TipoDocumentoPorParametro
DROP PROCEDURE IF EXISTS `SP_S_TipoDocumentoPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_TipoDocumentoPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT td.IdTipoDocumento,td.Descripcion FROM tipodocumento AS td WHERE td.IdTipoDocumento=pbusqueda;
	ELSEIF pcriterio = "descripcion" THEN
		SELECT td.IdTipoDocumento,td.Descripcion FROM tipodocumento AS td WHERE td.Descripcion LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT td.IdTipoDocumento,td.Descripcion FROM tipodocumento AS td;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_TipoUsuario
DROP PROCEDURE IF EXISTS `SP_S_TipoUsuario`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_TipoUsuario`(	
		
		)
BEGIN
		SELECT * FROM tipousuario;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_TipoUsuarioPorParametro
DROP PROCEDURE IF EXISTS `SP_S_TipoUsuarioPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_TipoUsuarioPorParametro`(	
			pcriterio varchar(20),
			pbusqueda varchar(20)		
		)
BEGIN
	IF pcriterio = "id" THEN
		SELECT * FROM tipousuario AS tp WHERE tp.IdTipoUsuario=pbusqueda;
	ELSEIF pcriterio = "descripcion" THEN
		SELECT * FROM tipousuario AS tp WHERE tp.Descripcion LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT * FROM tipousuario AS tp;
	END IF; 
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_UltimoIdCompra
DROP PROCEDURE IF EXISTS `SP_S_UltimoIdCompra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_UltimoIdCompra`(
	
	)
BEGIN
		SELECT MAX(IdCompra) AS id FROM compra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_UltimoIdVenta
DROP PROCEDURE IF EXISTS `SP_S_UltimoIdVenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_UltimoIdVenta`(
	
	)
BEGIN
		SELECT MAX(IdVenta) AS id FROM venta;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Venta
DROP PROCEDURE IF EXISTS `SP_S_Venta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Venta`(
	
	)
BEGIN
		SELECT v.IdVenta,td.Descripcion AS TipoDocumento,c.Nombre AS Cliente,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,v.Serie,v.Numero,v.Fecha,v.TotalVenta,v.Descuento,v.SubTotal,
		v.Igv,v.TotalPagar,v.Estado
		FROM venta AS v 
		INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
		INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
		INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
		ORDER BY v.IdVenta;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_VentaMensual
DROP PROCEDURE IF EXISTS `SP_S_VentaMensual`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_VentaMensual`(
		   pcriterio varchar(20),
			pfecha_ini varchar(20),
			pfecha_fin varchar(20)
	)
BEGIN
			IF pcriterio = "consultar" THEN
				SELECT CONCAT(UPPER(MONTHNAME(v.Fecha))," ",YEAR(v.Fecha)) AS Fecha,SUM(v.TotalVenta) AS Total,
				ROUND((SUM(v.TotalVenta)*100)/(SELECT SUM(v.TotalPagar) AS TotalVenta FROM venta AS v WHERE ((date_format(v.Fecha,'%Y-%m') >= pfecha_ini) AND (date_format(v.Fecha,'%Y-%m') <= pfecha_fin)) AND v.Estado="EMITIDO")) AS Porcentaje
				FROM venta AS v
				WHERE ((date_format(v.Fecha,'%Y-%m') >= pfecha_ini) AND (date_format(v.Fecha,'%Y-%m') <= pfecha_fin)) AND v.Estado="EMITIDO" GROUP BY v.Fecha;			
								
			END IF; 
			

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_VentaPorDetalle
DROP PROCEDURE IF EXISTS `SP_S_VentaPorDetalle`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_VentaPorDetalle`(
			pcriterio varchar(30),
			pfechaini date,
			pfechafin date
	)
BEGIN
		IF pcriterio = "consultar" THEN
			SELECT p.Codigo,p.Nombre AS Producto,c.Descripcion AS Categoria,dv.Costo,dv.Precio,
			SUM(dv.Cantidad) AS Cantidad,SUM(dv.Total) AS Total,
			SUM(TRUNCATE((Total-(dv.Costo*dv.Cantidad)),2)) AS Ganancia FROM venta AS v
			INNER JOIN detalleventa AS dv ON v.IdVenta=dv.IdVenta
			INNER JOIN producto AS p ON dv.IdProducto=p.IdProducto
			INNER JOIN categoria AS c ON p.IdCategoria=c.IdCategoria
			WHERE (v.Fecha>=pfechaini AND v.Fecha<=pfechafin) AND v.Estado="EMITIDO" GROUP BY p.IdProducto
			ORDER BY v.IdVenta DESC;
		END IF;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_VentaPorFecha
DROP PROCEDURE IF EXISTS `SP_S_VentaPorFecha`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_VentaPorFecha`(
			pcriterio varchar(30),
			pfechaini date,
			pfechafin date,
			pdocumento varchar(30)
	)
BEGIN
		IF pcriterio = "anular" THEN
			SELECT v.IdVenta,c.Nombre AS Cliente,v.Fecha,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,td.Descripcion AS TipoDocumento,v.Serie,v.Numero,
			v.Estado,v.TotalPagar  FROM venta AS v
			INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
			INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
			INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
			WHERE (v.Fecha>=pfechaini AND v.Fecha<=pfechafin) AND td.Descripcion=pdocumento ORDER BY v.IdVenta DESC;
		ELSEIF pcriterio = "consultar" THEN
		   SELECT v.IdVenta,c.Nombre AS Cliente,v.Fecha,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,td.Descripcion AS TipoDocumento,v.Serie,v.Numero,
			v.Estado,v.TotalVenta,v.Descuento,v.TotalPagar  FROM venta AS v
			INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
			INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
			INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
			WHERE (v.Fecha>=pfechaini AND v.Fecha<=pfechafin) ORDER BY v.IdVenta DESC;
		ELSEIF pcriterio = "caja" THEN	
		   SELECT SUM(dv.Cantidad) AS Cantidad,p.Nombre AS Producto,dv.Precio,
			SUM(dv.Total) AS Total, SUM(TRUNCATE((Total-(dv.Costo*dv.Cantidad)),2)) AS Ganancia,v.Fecha FROM venta AS v
			INNER JOIN detalleventa AS dv ON v.IdVenta=dv.IdVenta
			INNER JOIN producto AS p ON dv.IdProducto=p.IdProducto
			INNER JOIN categoria AS c ON p.IdCategoria=c.IdCategoria
			WHERE v.Fecha=pfechaini AND v.Estado="EMITIDO" GROUP BY p.IdProducto
			ORDER BY v.IdVenta DESC;
		END IF;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_VentaPorParametro
DROP PROCEDURE IF EXISTS `SP_S_VentaPorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_VentaPorParametro`(
		   pcriterio varchar(20),
			pbusqueda varchar(20)
	)
BEGIN
			IF pcriterio = "id" THEN
				SELECT v.IdVenta,td.Descripcion AS TipoDocumento,c.Nombre AS Cliente,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,v.Serie,v.Numero,v.Fecha,v.TotalVenta,v.Descuento,v.SubTotal,
				v.Igv,v.TotalPagar,v.Estado  FROM venta AS v
				INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
				INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
				INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
				WHERE v.IdVenta=pbusqueda ORDER BY v.IdVenta;
			ELSEIF pcriterio = "documento" THEN
				SELECT v.IdVenta,td.Descripcion AS TipoDocumento,c.Nombre AS Cliente,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,v.Serie,v.Numero,v.Fecha,v.TotalVenta,v.Descuento,v.SubTotal,
				v.Igv,v.TotalPagar,v.Estado  FROM venta AS v
				INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
				INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
				INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
				WHERE td.Descripcion=pbusqueda ORDER BY v.IdVenta;
			END IF; 
			

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_S_Venta_DetallePorParametro
DROP PROCEDURE IF EXISTS `SP_S_Venta_DetallePorParametro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Venta_DetallePorParametro`(
		   pcriterio varchar(20),
			pbusqueda varchar(20)
	)
BEGIN
			IF pcriterio = "id" THEN
				SELECT v.IdVenta,td.Descripcion AS TipoDocumento,c.Nombre AS Cliente,CONCAT(e.Nombre," ",e.Apellido) AS Empleado,v.Serie,v.Numero,v.Fecha,v.TotalVenta,v.Descuento,v.SubTotal,
				v.Igv,v.TotalPagar,v.Estado,p.Codigo,p.Nombre,dv.Cantidad,p.PrecioVenta,dv.Total  FROM venta AS v
				INNER JOIN tipodocumento AS td ON v.IdTipoDocumento=td.IdTipoDocumento
				INNER JOIN cliente AS c ON v.IdCliente=c.IdCliente
				INNER JOIN empleado AS e ON v.IdEmpleado=e.IdEmpleado
				INNER JOIN detalleventa AS dv ON v.IdVenta=dv.IdVenta
				INNER JOIN producto AS p ON dv.IdProducto=p.IdProducto
				WHERE v.IdVenta=pbusqueda ORDER BY v.IdVenta;
			
			END IF; 
			

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_ActualizarCompraEstado
DROP PROCEDURE IF EXISTS `SP_U_ActualizarCompraEstado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_ActualizarCompraEstado`(	
		   pidcompra int,
			pestado varchar(30)
		)
BEGIN
		UPDATE compra SET
			estado=pestado
		WHERE idcompra = pidcompra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_ActualizarProductoStock
DROP PROCEDURE IF EXISTS `SP_U_ActualizarProductoStock`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_ActualizarProductoStock`(	
		   pidproducto int,
			pstock decimal(8,2)
		)
BEGIN
		UPDATE producto SET
			stock=pstock
		WHERE idproducto = pidproducto;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_ActualizarVentaEstado
DROP PROCEDURE IF EXISTS `SP_U_ActualizarVentaEstado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_ActualizarVentaEstado`(	
		   pidventa int,
			pestado varchar(30)
		)
BEGIN
		UPDATE venta SET
			estado=pestado
		WHERE idventa = pidventa;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_CambiarPass
DROP PROCEDURE IF EXISTS `SP_U_CambiarPass`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_CambiarPass`(	
		   pidempleado int,
			pcontrasena text
		)
BEGIN
		UPDATE empleado SET
			contrasena=pcontrasena
		WHERE idempleado = pidempleado;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Categoria
DROP PROCEDURE IF EXISTS `SP_U_Categoria`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Categoria`(	
		   pidcategoria int,
			pdescripcion varchar(100)
		)
BEGIN
		UPDATE categoria SET
			descripcion=pdescripcion	
		WHERE idcategoria = pidcategoria;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Cliente
DROP PROCEDURE IF EXISTS `SP_U_Cliente`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Cliente`(	
		   pidcliente int,
		   pnombre varchar(100),
		   pruc varchar(11),
		   pdni varchar(8),
		   pdireccion varchar(50),
		   ptelefono varchar(15),
		   pobsv text
		)
BEGIN
		UPDATE cliente SET
			nombre=pnombre,
			ruc=pruc,
			dni=pdni,
			direccion=pdireccion,
			telefono=ptelefono,
			obsv=pobsv
		WHERE idcliente = pidcliente;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Compra
DROP PROCEDURE IF EXISTS `SP_U_Compra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Compra`(	
		   pidcompra int,
			pidtipodocumento int,
			pidproveedor int,
			pidempleado int,
			pnumero varchar(20),
			pfecha date,
			psubtotal decimal(8,2),
			pigv decimal(8,2),
			ptotal decimal(8,2),
			pestado varchar(30)
		)
BEGIN
		UPDATE compra SET
			idtipodocumento=pidtipodocumento,
			idproveedor=pidproveedor,
			idempleado=pidempleado,
			numero=pnumero,
			fecha=pfecha,
			subtotal=psubtotal,
			igv=pigv,
			total=ptotal,
			estado=pestado
		WHERE idcompra = pidcompra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Cuenta
DROP PROCEDURE IF EXISTS `SP_U_Cuenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Cuenta`(	
			pid int,
			pnombre varchar(100),
			pdescrpcion text,
			pcuentadefault bit,
			pbalance decimal
		)
BEGIN
	IF pcuentadefault = 1 THEN
		UPDATE cuenta SET
		CuentaDefault = 0;
	END IF; 

	UPDATE cuenta SET
		nombre=pnombre,
		descripcion=pdescrpcion,
		cuentadefault=pcuentadefault,
		balance=pbalance
	WHERE idcuenta = pid;

	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_DetalleCompra
DROP PROCEDURE IF EXISTS `SP_U_DetalleCompra`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_DetalleCompra`(	
			pidcompra int,
			pidproducto int,
			pcantidad decimal(8,2),
			pprecio decimal(8,2),
			ptotal decimal(8,2)
		)
BEGIN
		UPDATE venta SET
			idcompra=pidcompra,
			idproducto=pidproducto,
			cantidad=pcantidad,
			precio=pprecio,
			total=ptotal
		WHERE idcompra = pidcompra;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_DetalleVenta
DROP PROCEDURE IF EXISTS `SP_U_DetalleVenta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_DetalleVenta`(	
			pidventa int,
			pidproducto int,
			pcantidad decimal(8,2),
			pcosto decimal(8,2),
			pprecio decimal(8,2),
			ptotal decimal(8,2)
		)
BEGIN
		UPDATE venta SET
			idventa=pidventa,
			idproducto=pidproducto,
			cantidad=pcantidad,
			costo=pcosto,
			precio=pprecio,
			total=ptotal
		WHERE idventa = pidventa;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Empleado
DROP PROCEDURE IF EXISTS `SP_U_Empleado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Empleado`(	
		   pidempleado int,
		   pnombre varchar(50),
		   papellido varchar(80),
		   psexo varchar(1),
		   pfechanac date,
		   pdireccion varchar(100),
		   ptelefono varchar(10),
		   pcelular varchar(15),
		   pemail varchar(80),
		   pdni varchar(8),
			pfechaing date,
			psueldo decimal(8,2),
		   pestado varchar(30),
		   pusuario varchar(20),
		   pcontrasena text,
		   pidtipousuario int
		)
BEGIN
		UPDATE empleado SET
			nombre=pnombre,
			apellido=papellido,
			sexo=psexo,
			fechanac=pfechanac,
			direccion=pdireccion,
			telefono=ptelefono,
			celular=pcelular,
			email=pemail,
			dni=pdni,
			fechaing=pfechaing,
			sueldo=psueldo,
			estado=pestado,
			usuario=pusuario,
			contrasena=pcontrasena,
			idtipousuario=pidtipousuario			
		WHERE idempleado = pidempleado;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Producto
DROP PROCEDURE IF EXISTS `SP_U_Producto`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Producto`(	
		   pidproducto int,
			pcodigo varchar(50),
			pnombre varchar(100),
			pdescripcion text,
			pstock decimal(8,2),
			pstockmin decimal(8,2),
			ppreciocosto decimal(8,2),
			pprecioventa decimal(8,2),
			pprecioventa2 decimal(8,2),
			putilidad decimal(8,2),
			pestado varchar(30),
			pidcategoria int
		)
BEGIN
		UPDATE producto SET
			codigo=pcodigo,
			nombre=pnombre,
			descripcion=pdescripcion,
			stock=pstock,
			stockmin=pstockmin,
			preciocosto=ppreciocosto,
			precioventa=pprecioventa,
			precioventa2=pprecioventa2,
			utilidad=putilidad,
			estado=pestado,
			idcategoria=pidcategoria
			
		WHERE idproducto = pidproducto;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Proveedor
DROP PROCEDURE IF EXISTS `SP_U_Proveedor`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Proveedor`(	
		   pidproveedor int,
		   pnombre varchar(100),
			pruc varchar(11),
			pdni varchar(8),
			pdireccion varchar(100),
			ptelefono varchar(10),
			pcelular varchar(15),
			pemail varchar(80),
			pcuenta1 varchar(50),
			pcuenta2 varchar(50),
			pestado varchar(30),
			pobsv text
		)
BEGIN
		UPDATE proveedor SET
			nombre=pnombre,
			ruc=pruc,
			dni=pdni,
			direccion=pdireccion,
			telefono=ptelefono,
			celular=pcelular,
			email=pemail,
			cuenta1=pcuenta1,
			cuenta2=pcuenta2,
			estado=pestado,
			obsv=pobsv
		WHERE idproveedor = pidproveedor;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_TipoDocumento
DROP PROCEDURE IF EXISTS `SP_U_TipoDocumento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_TipoDocumento`(	
		   pidtipodocumento int,
			pdescripcion varchar(80)
		)
BEGIN
		UPDATE tipodocumento SET
			descripcion=pdescripcion	
		WHERE idtipodocumento = pidtipodocumento;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_TipoUsuario
DROP PROCEDURE IF EXISTS `SP_U_TipoUsuario`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_TipoUsuario`(	
		   pidtipousuario int,
			pdescripcion varchar(80),
			pp_venta int,
			pp_compra int,
			pp_producto int,
			pp_proveedor int,
			pp_empleado int,
			pp_cliente int,
			pp_categoria int,
			pp_tipodoc int,
			pp_tipouser int,
			pp_anularv int,
			pp_anularc int,
			pp_estadoprod int,
			pp_ventare int,
			pp_ventade int,
			pp_estadistica int,
			pp_comprare int,
			pp_comprade int,
			pp_pass int,
			pp_respaldar int,
			pp_restaurar int,
			pp_caja int,
			pp_cuentas int
		)
BEGIN
		UPDATE tipousuario SET
			descripcion=pdescripcion,
			p_venta=pp_venta,
			p_compra=pp_compra,
			p_producto=pp_producto,
			p_proveedor=pp_proveedor,
			p_empleado=pp_empleado,
			p_cliente=pp_cliente,
			p_categoria=pp_categoria,
			p_tipodoc=pp_tipodoc,
			p_tipouser=pp_tipouser,
			p_anularv=pp_anularv,
			p_anularc=pp_anularc,
			p_estadoprod=pp_estadoprod,
			p_ventare=pp_ventare,
			p_ventade=pp_ventade,
			p_estadistica=pp_estadistica,
			p_comprare=pp_comprare,
			p_comprade=pp_comprade,
			p_pass=pp_pass,
			p_respaldar=pp_respaldar,
			p_restaurar=pp_restaurar,
			p_caja=pp_caja,
			p_cuentas=pp_cuentas
		WHERE idtipousuario = pidtipousuario;
	END//
DELIMITER ;


-- Dumping structure for procedure db_casita.SP_U_Venta
DROP PROCEDURE IF EXISTS `SP_U_Venta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Venta`(	
		   pidventa int,
			pidtipodocumento int,
			pidcliente int,
			pidempleado int,
			pserie varchar(5),
			pnumero varchar(20),
			pfecha date,
			ptotalventa decimal(8,2),
			pdescuento decimal(8,2),
			psubtotal decimal(8,2),
			pigv decimal(8,2),
			ptotalpagar decimal(8,2),
			pestado varchar(30)
		)
BEGIN
		UPDATE venta SET
			idtipodocumento=pidtipodocumento,
			idcliente=pidcliente,
			idempleado=pidempleado,
			serie=pserie,
			numero=pnumero,
			fecha=pfecha,
			totalventa=ptotalventa,
			descuento=pdescuento,
			subtotal=psubtotal,
			igv=pigv,
			totalpagar=ptotalpagar,
			estado=pestado
		WHERE idventa = pidventa;
	END//
DELIMITER ;


-- Dumping structure for table db_casita.tipodocumento
DROP TABLE IF EXISTS `tipodocumento`;
CREATE TABLE IF NOT EXISTS `tipodocumento` (
  `IdTipoDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(80) NOT NULL,
  PRIMARY KEY (`IdTipoDocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.tipodocumento: ~3 rows (approximately)
/*!40000 ALTER TABLE `tipodocumento` DISABLE KEYS */;
INSERT INTO `tipodocumento` (`IdTipoDocumento`, `Descripcion`) VALUES
	(1, 'BOLETA'),
	(2, 'FACTURA'),
	(3, 'TICKET');
/*!40000 ALTER TABLE `tipodocumento` ENABLE KEYS */;


-- Dumping structure for table db_casita.tipousuario
DROP TABLE IF EXISTS `tipousuario`;
CREATE TABLE IF NOT EXISTS `tipousuario` (
  `IdTipoUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(20) NOT NULL,
  `p_venta` int(11) DEFAULT NULL,
  `p_compra` int(11) DEFAULT NULL,
  `p_producto` int(11) DEFAULT NULL,
  `p_proveedor` int(11) DEFAULT NULL,
  `p_empleado` int(11) DEFAULT NULL,
  `p_cliente` int(11) DEFAULT NULL,
  `p_categoria` int(11) DEFAULT NULL,
  `p_tipodoc` int(11) DEFAULT NULL,
  `p_tipouser` int(11) DEFAULT NULL,
  `p_anularv` int(11) DEFAULT NULL,
  `p_anularc` int(11) DEFAULT NULL,
  `p_estadoprod` int(11) DEFAULT NULL,
  `p_ventare` int(11) DEFAULT NULL,
  `p_ventade` int(11) DEFAULT NULL,
  `p_estadistica` int(11) DEFAULT NULL,
  `p_comprare` int(11) DEFAULT NULL,
  `p_comprade` int(11) DEFAULT NULL,
  `p_pass` int(11) DEFAULT NULL,
  `p_respaldar` int(11) DEFAULT NULL,
  `p_restaurar` int(11) DEFAULT NULL,
  `p_caja` int(11) DEFAULT NULL,
  `p_cuentas` int(11) DEFAULT NULL,
  `p_descuento` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdTipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.tipousuario: ~6 rows (approximately)
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` (`IdTipoUsuario`, `Descripcion`, `p_venta`, `p_compra`, `p_producto`, `p_proveedor`, `p_empleado`, `p_cliente`, `p_categoria`, `p_tipodoc`, `p_tipouser`, `p_anularv`, `p_anularc`, `p_estadoprod`, `p_ventare`, `p_ventade`, `p_estadistica`, `p_comprare`, `p_comprade`, `p_pass`, `p_respaldar`, `p_restaurar`, `p_caja`, `p_cuentas`, `p_descuento`) VALUES
	(1, 'ADMINISTRADOR', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0),
	(2, 'CAJERO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(3, 'SINCUENTAS', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, NULL),
	(4, 'SOLOCUENTAS', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
	(5, 'DESCUENTOS', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	(6, 'DEMOA', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;


-- Dumping structure for table db_casita.venta
DROP TABLE IF EXISTS `venta`;
CREATE TABLE IF NOT EXISTS `venta` (
  `IdVenta` int(11) NOT NULL AUTO_INCREMENT,
  `IdTipoDocumento` int(11) NOT NULL,
  `IdCliente` int(11) NOT NULL,
  `IdEmpleado` int(11) NOT NULL,
  `Serie` varchar(5) DEFAULT NULL,
  `Numero` varchar(20) DEFAULT NULL,
  `Fecha` date NOT NULL,
  `TotalVenta` decimal(8,2) NOT NULL,
  `Descuento` decimal(8,2) NOT NULL,
  `SubTotal` decimal(8,2) NOT NULL,
  `Igv` decimal(8,2) NOT NULL,
  `TotalPagar` decimal(8,2) NOT NULL,
  `Estado` varchar(30) NOT NULL,
  PRIMARY KEY (`IdVenta`),
  KEY `fk_Venta_TipoDocumento1_idx` (`IdTipoDocumento`),
  KEY `fk_Venta_Cliente1_idx` (`IdCliente`),
  KEY `fk_Venta_Empleado1_idx` (`IdEmpleado`),
  CONSTRAINT `fk_Venta_Cliente1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`IdCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venta_Empleado1` FOREIGN KEY (`IdEmpleado`) REFERENCES `empleado` (`IdEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venta_TipoDocumento1` FOREIGN KEY (`IdTipoDocumento`) REFERENCES `tipodocumento` (`IdTipoDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Dumping data for table db_casita.venta: ~16 rows (approximately)
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` (`IdVenta`, `IdTipoDocumento`, `IdCliente`, `IdEmpleado`, `Serie`, `Numero`, `Fecha`, `TotalVenta`, `Descuento`, `SubTotal`, `Igv`, `TotalPagar`, `Estado`) VALUES
	(1, 1, 1, 2, '001', 'C00001', '2015-02-06', 140.00, 0.00, 118.64, 21.36, 140.00, 'EMITIDO'),
	(2, 1, 1, 2, '001', 'C00002', '2015-02-06', 140.00, 0.00, 118.64, 21.36, 140.00, 'EMITIDO'),
	(3, 1, 1, 2, '001', 'C00003', '2015-02-18', 453.80, 0.00, 384.58, 69.22, 453.80, 'EMITIDO'),
	(4, 1, 1, 2, '001', 'C00004', '2015-02-19', 585.00, 0.00, 495.76, 89.24, 585.00, 'EMITIDO'),
	(5, 1, 1, 2, '001', 'C00005', '2015-02-19', 750.20, 0.00, 635.76, 114.44, 750.20, 'EMITIDO'),
	(6, 1, 1, 2, '001', 'C00006', '2015-02-25', 150.00, 0.00, 127.12, 22.88, 127.12, 'EMITIDO'),
	(7, 2, 2, 2, '001', 'C00007', '2015-02-25', 300.00, 0.00, 254.24, 45.76, 254.24, 'EMITIDO'),
	(10, 1, 1, 2, '001', 'C00008', '2015-02-25', 150.00, 0.00, 127.12, 22.88, 150.00, 'EMITIDO'),
	(11, 1, 1, 2, '001', 'C00011', '2015-02-25', 150.00, 0.00, 127.12, 22.88, 150.00, 'EMITIDO'),
	(12, 1, 1, 2, '001', 'C00012', '2015-02-25', 150.00, 0.00, 127.12, 22.88, 150.00, 'EMITIDO'),
	(13, 3, 1, 2, '001', 'C00013', '2015-02-25', 310.00, 0.00, 262.71, 47.29, 310.00, 'EMITIDO'),
	(14, 1, 1, 2, '001', 'C00014', '2015-02-26', 310.00, 0.00, 262.71, 47.29, 310.00, 'EMITIDO'),
	(15, 1, 1, 2, '001', 'C00015', '2015-02-26', 150.00, 0.00, 127.12, 22.88, 150.00, 'EMITIDO'),
	(16, 1, 1, 2, '001', 'C00016', '2015-02-26', 5236.90, 0.00, 4438.05, 798.85, 5236.90, 'EMITIDO'),
	(17, 1, 1, 2, '001', 'C00017', '2015-02-27', 310.00, 0.00, 262.71, 47.29, 310.00, 'EMITIDO'),
	(18, 1, 1, 2, '001', 'C00018', '2015-03-01', 10.00, 0.00, 8.47, 1.52, 10.00, 'EMITIDO');
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;


-- Dumping structure for trigger db_casita.operacion_AFTER_INSERT
DROP TRIGGER IF EXISTS `operacion_AFTER_INSERT`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `operacion_AFTER_INSERT` AFTER INSERT ON `operacion` FOR EACH ROW UPDATE cuenta c SET c.Balance=NEW.montoFinal WHERE c.IdCuenta=NEW.cuenta//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
