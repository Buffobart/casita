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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadClienteHib.findAll", query = "SELECT c FROM ClsEntidadClienteHib c"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByIdCliente", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByNombre", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByRuc", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.ruc = :ruc"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByDni", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.dni = :dni"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByDireccion", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ClsEntidadClienteHib.findByTelefono", query = "SELECT c FROM ClsEntidadClienteHib c WHERE c.telefono = :telefono")})
public class ClsEntidadClienteHib implements Serializable, IntTercero {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCliente")
    private Integer idCliente;
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
    @Lob
    @Column(name = "Obsv")
    private String obsv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection;

    public ClsEntidadClienteHib() {
    }

    public ClsEntidadClienteHib(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public ClsEntidadClienteHib(Integer idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public String getObsv() {
        return obsv;
    }

    public void setObsv(String obsv) {
        this.obsv = obsv;
    }

    @XmlTransient
    public Collection<ClsEntidadVentaHib> getClsEntidadVentaHibCollection() {
        return clsEntidadVentaHibCollection;
    }

    public void setClsEntidadVentaHibCollection(Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection) {
        this.clsEntidadVentaHibCollection = clsEntidadVentaHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadClienteHib)) {
            return false;
        }
        ClsEntidadClienteHib other = (ClsEntidadClienteHib) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadClienteHib[ idCliente=" + idCliente + " ]";
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    @Override
    public String getRFC() {
        return this.getRuc();
    }

    @Override
    public Integer getId() {
        return this.getIdCliente();
    }
    
}
