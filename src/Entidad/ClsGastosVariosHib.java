/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "gastos_varios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsGastosVariosHib.findAll", query = "SELECT c FROM ClsGastosVariosHib c"),
    @NamedQuery(name = "ClsGastosVariosHib.findById", query = "SELECT c FROM ClsGastosVariosHib c WHERE c.id = :id"),
    @NamedQuery(name = "ClsGastosVariosHib.findByDescripcion", query = "SELECT c FROM ClsGastosVariosHib c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ClsGastosVariosHib.findByCantidad", query = "SELECT c FROM ClsGastosVariosHib c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ClsGastosVariosHib.findByFecha", query = "SELECT c FROM ClsGastosVariosHib c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ClsGastosVariosHib.findByTotal", query = "SELECT c FROM ClsGastosVariosHib c WHERE c.total = :total")})
public class ClsGastosVariosHib implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @JoinColumn(name = "USUARIO", referencedColumnName = "IdEmpleado")
    @ManyToOne
    private ClsEntidadEmpleadoHib usuario;
    @JoinColumn(name = "CUENTA", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuenta;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "IdProducto")
    @ManyToOne
    private ClsEntidadProductoHib idProducto;

    public ClsGastosVariosHib() {
    }

    public ClsGastosVariosHib(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ClsEntidadEmpleadoHib getUsuario() {
        return usuario;
    }

    public void setUsuario(ClsEntidadEmpleadoHib usuario) {
        this.usuario = usuario;
    }

    public ClsEntidadCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClsEntidadCuenta cuenta) {
        this.cuenta = cuenta;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsGastosVariosHib)) {
            return false;
        }
        ClsGastosVariosHib other = (ClsGastosVariosHib) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsGastosVariosHib[ id=" + id + " ]";
    }
    
}
