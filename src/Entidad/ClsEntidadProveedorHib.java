/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadProveedorHib.findAll", query = "SELECT c FROM ClsEntidadProveedorHib c"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByIdProveedor", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.idProveedor = :idProveedor"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByNombre", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByRuc", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.ruc = :ruc"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByDni", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.dni = :dni"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByDireccion", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByTelefono", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByCelular", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.celular = :celular"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByEmail", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.email = :email"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByCuenta1", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.cuenta1 = :cuenta1"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByCuenta2", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.cuenta2 = :cuenta2"),
    @NamedQuery(name = "ClsEntidadProveedorHib.findByEstado", query = "SELECT c FROM ClsEntidadProveedorHib c WHERE c.estado = :estado")})
public class ClsEntidadProveedorHib implements Serializable, IntTercero {
    @OneToMany(mappedBy = "idProveedor", fetch=FetchType.EAGER)
    private Collection<ClsEntidadProveedorContactoHib> clsEntidadProveedorContactoHibCollection;
    @OneToMany(mappedBy = "proveedor")
    private Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProveedor")
    private Integer idProveedor;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Ruc")
    private String ruc;
    @Column(name = "Dni")
    private String dni;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "Celular")
    private String celular;
    @Column(name = "Email")
    private String email;
    @Column(name = "Cuenta1")
    private String cuenta1;
    @Column(name = "Cuenta2")
    private String cuenta2;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @Lob
    @Column(name = "Obsv")
    private String obsv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProveedor")
    private Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection;

    public ClsEntidadProveedorHib() {
    }

    public ClsEntidadProveedorHib(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ClsEntidadProveedorHib(Integer idProveedor, String nombre, String estado) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuenta1() {
        return cuenta1;
    }

    public void setCuenta1(String cuenta1) {
        this.cuenta1 = cuenta1;
    }

    public String getCuenta2() {
        return cuenta2;
    }

    public void setCuenta2(String cuenta2) {
        this.cuenta2 = cuenta2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObsv() {
        return obsv;
    }

    public void setObsv(String obsv) {
        this.obsv = obsv;
    }

    @XmlTransient
    public Collection<ClsEntidadCompraHib> getClsEntidadCompraHibCollection() {
        return clsEntidadCompraHibCollection;
    }

    public void setClsEntidadCompraHibCollection(Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection) {
        this.clsEntidadCompraHibCollection = clsEntidadCompraHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadProveedorHib)) {
            return false;
        }
        ClsEntidadProveedorHib other = (ClsEntidadProveedorHib) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadProveedorHib[ idProveedor=" + idProveedor + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadProductoHib> getClsEntidadProductoHibCollection() {
        return clsEntidadProductoHibCollection;
    }

    public void setClsEntidadProductoHibCollection(Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection) {
        this.clsEntidadProductoHibCollection = clsEntidadProductoHibCollection;
    }

    @XmlTransient
    public Collection<ClsEntidadProveedorContactoHib> getClsEntidadProveedorContactoHibCollection() {
        return clsEntidadProveedorContactoHibCollection;
    }

    public void setClsEntidadProveedorContactoHibCollection(Collection<ClsEntidadProveedorContactoHib> clsEntidadProveedorContactoHibCollection) {
        this.clsEntidadProveedorContactoHibCollection = clsEntidadProveedorContactoHibCollection;
    }

    @Override
    public String getTipo() {
        return "Proveedor";
    }

    @Override
    public String getRFC() {
        return this.getRuc();
    }

    @Override
    public Integer getId() {
        return this.getIdProveedor();
    }
    
}
