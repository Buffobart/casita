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
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadCategoriaHib.findAll", query = "SELECT c FROM ClsEntidadCategoriaHib c"),
    @NamedQuery(name = "ClsEntidadCategoriaHib.findByIdCategoria", query = "SELECT c FROM ClsEntidadCategoriaHib c WHERE c.idCategoria = :idCategoria"),
    @NamedQuery(name = "ClsEntidadCategoriaHib.findByDescripcion", query = "SELECT c FROM ClsEntidadCategoriaHib c WHERE c.descripcion = :descripcion")})
public class ClsEntidadCategoriaHib implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCategoria")
    private Integer idCategoria;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoria")
    private Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection;

    public ClsEntidadCategoriaHib() {
    }

    public ClsEntidadCategoriaHib(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public ClsEntidadCategoriaHib(Integer idCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<ClsEntidadProductoHib> getClsEntidadProductoHibCollection() {
        return clsEntidadProductoHibCollection;
    }

    public void setClsEntidadProductoHibCollection(Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection) {
        this.clsEntidadProductoHibCollection = clsEntidadProductoHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadCategoriaHib)) {
            return false;
        }
        ClsEntidadCategoriaHib other = (ClsEntidadCategoriaHib) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadCategoriaHib[ idCategoria=" + idCategoria + " ]";
    }
    
}
