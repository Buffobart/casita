/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "detalleventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findAll", query = "SELECT c FROM ClsEntidadDetalleventaHib c"),
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findByIdDetalleVenta", query = "SELECT c FROM ClsEntidadDetalleventaHib c WHERE c.idDetalleVenta = :idDetalleVenta"),
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findByCantidad", query = "SELECT c FROM ClsEntidadDetalleventaHib c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findByCosto", query = "SELECT c FROM ClsEntidadDetalleventaHib c WHERE c.costo = :costo"),
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findByPrecio", query = "SELECT c FROM ClsEntidadDetalleventaHib c WHERE c.precio = :precio"),
    @NamedQuery(name = "ClsEntidadDetalleventaHib.findByTotal", query = "SELECT c FROM ClsEntidadDetalleventaHib c WHERE c.total = :total")})
public class ClsEntidadDetalleventaHib implements Serializable, IntEntidadDetalleImprimible {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleVenta")
    private Integer idDetalleVenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private BigDecimal cantidad;
    @Basic(optional = false)
    @Column(name = "Costo")
    private BigDecimal costo;
    @Basic(optional = false)
    @Column(name = "Precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "Total")
    private BigDecimal total;
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne(optional = false)
    private ClsEntidadProductoHib idProducto;
    @JoinColumn(name = "IdVenta", referencedColumnName = "IdVenta")
    @ManyToOne(optional = false)
    private ClsEntidadVentaHib idVenta;

    public ClsEntidadDetalleventaHib() {
    }

    public ClsEntidadDetalleventaHib(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public ClsEntidadDetalleventaHib(Integer idDetalleVenta, BigDecimal cantidad, BigDecimal costo, BigDecimal precio, BigDecimal total) {
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.costo = costo;
        this.precio = precio;
        this.total = total;
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ClsEntidadProductoHib getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ClsEntidadProductoHib idProducto) {
        this.idProducto = idProducto;
    }

    public ClsEntidadVentaHib getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(ClsEntidadVentaHib idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleVenta != null ? idDetalleVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadDetalleventaHib)) {
            return false;
        }
        ClsEntidadDetalleventaHib other = (ClsEntidadDetalleventaHib) object;
        if ((this.idDetalleVenta == null && other.idDetalleVenta != null) || (this.idDetalleVenta != null && !this.idDetalleVenta.equals(other.idDetalleVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadDetalleventaHib[ idDetalleVenta=" + idDetalleVenta + " ]";
    }

    @Override
    public Integer getIdDetalle() {
        return this.getIdDetalleVenta();
    }
    
}
