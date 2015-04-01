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
@Table(name = "detallecompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadDetallecompraHib.findAll", query = "SELECT c FROM ClsEntidadDetallecompraHib c"),
    @NamedQuery(name = "ClsEntidadDetallecompraHib.findByIdDetalleCompra", query = "SELECT c FROM ClsEntidadDetallecompraHib c WHERE c.idDetalleCompra = :idDetalleCompra"),
    @NamedQuery(name = "ClsEntidadDetallecompraHib.findByCantidad", query = "SELECT c FROM ClsEntidadDetallecompraHib c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ClsEntidadDetallecompraHib.findByPrecio", query = "SELECT c FROM ClsEntidadDetallecompraHib c WHERE c.precio = :precio"),
    @NamedQuery(name = "ClsEntidadDetallecompraHib.findByTotal", query = "SELECT c FROM ClsEntidadDetallecompraHib c WHERE c.total = :total")})
public class ClsEntidadDetallecompraHib implements Serializable, IntEntidadDetalleImprimible {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleCompra")
    private Integer idDetalleCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private BigDecimal cantidad;
    @Basic(optional = false)
    @Column(name = "Precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "Total")
    private BigDecimal total;
    @JoinColumn(name = "IdCompra", referencedColumnName = "IdCompra")
    @ManyToOne(optional = false)
    private ClsEntidadCompraHib idCompra;
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne(optional = false)
    private ClsEntidadProductoHib idProducto;

    public ClsEntidadDetallecompraHib() {
    }

    public ClsEntidadDetallecompraHib(Integer idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public ClsEntidadDetallecompraHib(Integer idDetalleCompra, BigDecimal cantidad, BigDecimal precio, BigDecimal total) {
        this.idDetalleCompra = idDetalleCompra;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public Integer getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(Integer idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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

    public ClsEntidadCompraHib getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(ClsEntidadCompraHib idCompra) {
        this.idCompra = idCompra;
    }

    public ClsEntidadProductoHib getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ClsEntidadProductoHib idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleCompra != null ? idDetalleCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadDetallecompraHib)) {
            return false;
        }
        ClsEntidadDetallecompraHib other = (ClsEntidadDetallecompraHib) object;
        if ((this.idDetalleCompra == null && other.idDetalleCompra != null) || (this.idDetalleCompra != null && !this.idDetalleCompra.equals(other.idDetalleCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadDetallecompraHib[ idDetalleCompra=" + idDetalleCompra + " ]";
    }

    @Override
    public Integer getIdDetalle() {
        return this.getIdDetalleCompra();
    }

    @Override
    public BigDecimal getCosto() {
        return this.getPrecio();
    }
    
}
