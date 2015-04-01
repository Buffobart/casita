/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
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
@Table(name = "proveedorcontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findAll", query = "SELECT c FROM ClsEntidadProveedorContactoHib c"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findById", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.id = :id"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findByNombre", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findByTelefono", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findByExt", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.ext = :ext"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findByCorreo", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.correo = :correo"),
    @NamedQuery(name = "ClsEntidadProveedorContactoHib.findByPuesto", query = "SELECT c FROM ClsEntidadProveedorContactoHib c WHERE c.puesto = :puesto")})
public class ClsEntidadProveedorContactoHib implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "ext")
    private String ext;
    @Column(name = "correo")
    private String correo;
    @Column(name = "puesto")
    private String puesto;
    @JoinColumn(name = "idProveedor", referencedColumnName = "IdProveedor")
    @ManyToOne
    private ClsEntidadProveedorHib idProveedor;

    public ClsEntidadProveedorContactoHib() {
    }

    public ClsEntidadProveedorContactoHib(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public ClsEntidadProveedorHib getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ClsEntidadProveedorHib idProveedor) {
        this.idProveedor = idProveedor;
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
        if (!(object instanceof ClsEntidadProveedorContactoHib)) {
            return false;
        }
        ClsEntidadProveedorContactoHib other = (ClsEntidadProveedorContactoHib) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadProveedorContactoHib[ id=" + id + " ]";
    }
    
}
