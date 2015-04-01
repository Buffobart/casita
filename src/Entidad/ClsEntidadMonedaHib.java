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
@Table(name = "moneda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadMonedaHib.findAll", query = "SELECT c FROM ClsEntidadMonedaHib c"),
    @NamedQuery(name = "ClsEntidadMonedaHib.findById", query = "SELECT c FROM ClsEntidadMonedaHib c WHERE c.id = :id"),
    @NamedQuery(name = "ClsEntidadMonedaHib.findByNombre", query = "SELECT c FROM ClsEntidadMonedaHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadMonedaHib.findByTipoCambio", query = "SELECT c FROM ClsEntidadMonedaHib c WHERE c.tipoCambio = :tipoCambio")})
public class ClsEntidadMonedaHib implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moneda")
    private Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipoCambio")
    private BigDecimal tipoCambio;

    public ClsEntidadMonedaHib() {
    }

    public ClsEntidadMonedaHib(Integer id) {
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

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
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
        if (!(object instanceof ClsEntidadMonedaHib)) {
            return false;
        }
        ClsEntidadMonedaHib other = (ClsEntidadMonedaHib) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadMonedaHib[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadProductoHib> getClsEntidadProductoHibCollection() {
        return clsEntidadProductoHibCollection;
    }

    public void setClsEntidadProductoHibCollection(Collection<ClsEntidadProductoHib> clsEntidadProductoHibCollection) {
        this.clsEntidadProductoHibCollection = clsEntidadProductoHibCollection;
    }
    
}
