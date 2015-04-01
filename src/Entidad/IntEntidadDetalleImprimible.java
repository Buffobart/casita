/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.math.BigDecimal;

/**
 *
 * @author Alan
 */
public interface IntEntidadDetalleImprimible {
    public Integer getIdDetalle();
    public BigDecimal getCantidad();
    public BigDecimal getPrecio();
    public BigDecimal getCosto();
    public BigDecimal getTotal();
    public ClsEntidadProductoHib getIdProducto();
}
