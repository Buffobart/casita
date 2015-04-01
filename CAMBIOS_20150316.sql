CREATE TABLE `db_casita`.`proveedorcontacto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `telefono` VARCHAR(45) NULL,
  `ext` VARCHAR(45) NULL,
  `correo` VARCHAR(45) NULL,
  `puesto` VARCHAR(45) NULL,
  `idProveedor` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_proveedor_idx` (`idProveedor` ASC),
  CONSTRAINT `fk_proveedor_contacto`
    FOREIGN KEY (`idProveedor`)
    REFERENCES `db_casita`.`proveedor` (`IdProveedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
