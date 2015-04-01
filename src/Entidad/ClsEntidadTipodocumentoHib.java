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
@Table(name = "tipodocumento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadTipodocumentoHib.findAll", query = "SELECT c FROM ClsEntidadTipodocumentoHib c"),
    @NamedQuery(name = "ClsEntidadTipodocumentoHib.findByIdTipoDocumento", query = "SELECT c FROM ClsEntidadTipodocumentoHib c WHERE c.idTipoDocumento = :idTipoDocumento"),
    @NamedQuery(name = "ClsEntidadTipodocumentoHib.findByDescripcion", query = "SELECT c FROM ClsEntidadTipodocumentoHib c WHERE c.descripcion = :descripcion")})
public class ClsEntidadTipodocumentoHib implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDocumento")
    private Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdTipoDocumento")
    private Integer idTipoDocumento;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDocumento")
    private Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection;

    public ClsEntidadTipodocumentoHib() {
    }

    public ClsEntidadTipodocumentoHib(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public ClsEntidadTipodocumentoHib(Integer idTipoDocumento, String descripcion) {
        this.idTipoDocumento = idTipoDocumento;
        this.descripcion = descripcion;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idTipoDocumento != null ? idTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadTipodocumentoHib)) {
            return false;
        }
        ClsEntidadTipodocumentoHib other = (ClsEntidadTipodocumentoHib) object;
        if ((this.idTipoDocumento == null && other.idTipoDocumento != null) || (this.idTipoDocumento != null && !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadTipodocumentoHib[ idTipoDocumento=" + idTipoDocumento + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadVentaHib> getClsEntidadVentaHibCollection() {
        return clsEntidadVentaHibCollection;
    }

    public void setClsEntidadVentaHibCollection(Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection) {
        this.clsEntidadVentaHibCollection = clsEntidadVentaHibCollection;
    }
    
}
