/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadCuenta.findAll", query = "SELECT c FROM ClsEntidadCuenta c"),
    @NamedQuery(name = "ClsEntidadCuenta.findByIdCuenta", query = "SELECT c FROM ClsEntidadCuenta c WHERE c.idCuenta = :idCuenta"),
    @NamedQuery(name = "ClsEntidadCuenta.findByNombre", query = "SELECT c FROM ClsEntidadCuenta c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadCuenta.findByCuentaDefault", query = "SELECT c FROM ClsEntidadCuenta c WHERE c.cuentaDefault = :cuentaDefault"),
    @NamedQuery(name = "ClsEntidadCuenta.findByBalance", query = "SELECT c FROM ClsEntidadCuenta c WHERE c.balance = :balance")})
public class ClsEntidadCuenta implements Serializable {
    @OneToMany(mappedBy = "cuenta")
    private Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection;
    @OneToMany(mappedBy = "cuenta")
    private Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCuenta")
    private Integer idCuenta;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "CuentaDefault")
    private Boolean cuentaDefault;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Balance")
    private BigDecimal balance;
    @OneToMany(mappedBy = "cuenta")
    private Collection<ClsGastosVariosHib> clsGastosVariosHibCollection;
    @OneToMany(mappedBy = "cuentaOrigen")
    private Collection<ClsTransferenciaHib> clsTransferenciaHibCollection;
    @OneToMany(mappedBy = "cuentaDestino")
    private Collection<ClsTransferenciaHib> clsTransferenciaHibCollection1;
    @OneToMany(mappedBy = "cuenta")
    private Collection<ClsEntidadEmpleadoHib> clsEntidadEmpleadoHibCollection;

    public ClsEntidadCuenta() {
    }

    public ClsEntidadCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public ClsEntidadCuenta(Integer idCuenta, String nombre) {
        this.idCuenta = idCuenta;
        this.nombre = nombre;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getCuentaDefault() {
        return cuentaDefault;
    }

    public void setCuentaDefault(Boolean cuentaDefault) {
        this.cuentaDefault = cuentaDefault;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @XmlTransient
    public Collection<ClsGastosVariosHib> getClsGastosVariosHibCollection() {
        return clsGastosVariosHibCollection;
    }

    public void setClsGastosVariosHibCollection(Collection<ClsGastosVariosHib> clsGastosVariosHibCollection) {
        this.clsGastosVariosHibCollection = clsGastosVariosHibCollection;
    }

    @XmlTransient
    public Collection<ClsTransferenciaHib> getClsTransferenciaHibCollection() {
        return clsTransferenciaHibCollection;
    }

    public void setClsTransferenciaHibCollection(Collection<ClsTransferenciaHib> clsTransferenciaHibCollection) {
        this.clsTransferenciaHibCollection = clsTransferenciaHibCollection;
    }

    @XmlTransient
    public Collection<ClsTransferenciaHib> getClsTransferenciaHibCollection1() {
        return clsTransferenciaHibCollection1;
    }

    public void setClsTransferenciaHibCollection1(Collection<ClsTransferenciaHib> clsTransferenciaHibCollection1) {
        this.clsTransferenciaHibCollection1 = clsTransferenciaHibCollection1;
    }

    @XmlTransient
    public Collection<ClsEntidadEmpleadoHib> getClsEntidadEmpleadoHibCollection() {
        return clsEntidadEmpleadoHibCollection;
    }

    public void setClsEntidadEmpleadoHibCollection(Collection<ClsEntidadEmpleadoHib> clsEntidadEmpleadoHibCollection) {
        this.clsEntidadEmpleadoHibCollection = clsEntidadEmpleadoHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadCuenta)) {
            return false;
        }
        ClsEntidadCuenta other = (ClsEntidadCuenta) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadCuenta[ idCuenta=" + idCuenta + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadCompraHib> getClsEntidadCompraHibCollection() {
        return clsEntidadCompraHibCollection;
    }

    public void setClsEntidadCompraHibCollection(Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection) {
        this.clsEntidadCompraHibCollection = clsEntidadCompraHibCollection;
    }

    @XmlTransient
    public Collection<ClsEntidadVentaHib> getClsEntidadVentaHibCollection() {
        return clsEntidadVentaHibCollection;
    }

    public void setClsEntidadVentaHibCollection(Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection) {
        this.clsEntidadVentaHibCollection = clsEntidadVentaHibCollection;
    }
    
}
