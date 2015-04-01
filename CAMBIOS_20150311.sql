ALTER TABLE `db_casita`.`producto` 
ADD COLUMN `Proveedor` INT NULL AFTER `Moneda`,
ADD INDEX `fk_proveedor_idx` (`Proveedor` ASC);
ALTER TABLE `db_casita`.`producto` 
ADD CONSTRAINT `fk_proveedor`
  FOREIGN KEY (`Proveedor`)
  REFERENCES `db_casita`.`proveedor` (`IdProveedor`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
