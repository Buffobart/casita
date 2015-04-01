	DROP PROCEDURE IF EXISTS `SP_S_CompraPorID`;
	
	DELIMITER //
	CREATE PROCEDURE SP_S_CompraPorID
	(
		pidcompra int
	)
	BEGIN
		SELECT c.IdCompra,c.Fecha,c.Numero,c.Estado,c.Total,p.Nombre,c.Igv,c.SubTotal AS Proveedor, p.Ruc AS Ruc, p.IdProveedor AS IdProveedor, p.Telefono AS Telefono, p.Direccion AS Direccion
		FROM compra AS c
		INNER JOIN proveedor AS p ON c.IdProveedor=p.IdProveedor
		WHERE c.IdCompra = pidcompra ORDER BY c.IdCompra DESC;
	END;//
	DELIMITER &&
	
	
	CREATE TABLE `db_casita`.`moneda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `tipoCambio` DECIMAL(8,4) NULL,
  PRIMARY KEY (`id`));

  INSERT INTO `db_casita`.`moneda` (`nombre`, `tipoCambio`) VALUES ('Peso (MXN)', '1');
INSERT INTO `db_casita`.`moneda` (`nombre`, `tipoCambio`) VALUES ('Dolar (USD)', '15.0015');


ALTER TABLE `db_casita`.`producto` 
ADD COLUMN `Moneda` INT NOT NULL DEFAULT 1 AFTER `DescuentoVenta`,
ADD INDEX `fk_Moneda_idx` (`Moneda` ASC);
ALTER TABLE `db_casita`.`producto` 
ADD CONSTRAINT `fk_Moneda`
  FOREIGN KEY (`Moneda`)
  REFERENCES `db_casita`.`moneda` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  CREATE TABLE `db_casita`.`operacion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NULL,
  `cuenta` INT NULL,
  `cantidad` DECIMAL(8,2) NULL,
  `montoInicial` DECIMAL(8,2) NULL,
  `montoFinal` DECIMAL(8,2) NULL,
  `usuario` INT NULL,
  `hora` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_usuario_idx` (`usuario` ASC),
  INDEX `fk_cuenta_idx` (`cuenta` ASC),
  CONSTRAINT `fk_usuario`
    FOREIGN KEY (`usuario`)
    REFERENCES `db_casita`.`empleado` (`IdEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuenta`
    FOREIGN KEY (`cuenta`)
    REFERENCES `db_casita`.`cuenta` (`IdCuenta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
	
USE `db_casita`;

DELIMITER $$

DROP TRIGGER IF EXISTS db_casita.operacion_AFTER_INSERT$$
USE `db_casita`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `db_casita`.`operacion_AFTER_INSERT` AFTER INSERT ON `operacion` FOR EACH ROW
    UPDATE cuenta c SET c.Balance=NEW.montoFinal WHERE c.IdCuenta=NEW.cuenta;$$
DELIMITER ;

