/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Alan
 */
public interface IntEntidadTransaccionImprimible {
    public Integer getId();
    public String getSerie();
    public String getNumero();
    public Date getFecha();
    public BigDecimal getTotal();
    public BigDecimal getSubTotal();
    public ClsEntidadProveedorHib getIdProveedor();
    public BigDecimal getIgv();
    public BigDecimal getTotalPagar();
    public Collection getClsEntidadDetalleCollection();
    public IntTercero getDatosDelTercero();
    //public String getTipoTransaccion();
    //public void setTipoTransaccion(String tipoTransaccion);
}
