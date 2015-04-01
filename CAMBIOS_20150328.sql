ALTER TABLE `db_casita`.`empleado` 
ADD COLUMN `cuenta` INT NULL AFTER `IdTipoUsuario`,
ADD INDEX `fk_empleado_cuenta_idx` (`cuenta` ASC);
ALTER TABLE `db_casita`.`empleado` 
ADD CONSTRAINT `fk_empleado_cuenta`
  FOREIGN KEY (`cuenta`)
  REFERENCES `db_casita`.`cuenta` (`IdCuenta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

