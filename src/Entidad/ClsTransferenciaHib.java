/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Presentacion.FrmPrincipal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Table(name = "transferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsTransferenciaHib.findAll", query = "SELECT c FROM ClsTransferenciaHib c"),
    @NamedQuery(name = "ClsTransferenciaHib.findById", query = "SELECT c FROM ClsTransferenciaHib c WHERE c.id = :id"),
    @NamedQuery(name = "ClsTransferenciaHib.findByDescripcion", query = "SELECT c FROM ClsTransferenciaHib c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ClsTransferenciaHib.findByFecha", query = "SELECT c FROM ClsTransferenciaHib c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ClsTransferenciaHib.findByCantidad", query = "SELECT c FROM ClsTransferenciaHib c WHERE c.cantidad = :cantidad")})
public class ClsTransferenciaHib implements Serializable, IntOperacion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @JoinColumn(name = "CUENTA_ORIGEN", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuentaOrigen;
    @JoinColumn(name = "CUENTA_DESTINO", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuentaDestino;
    @JoinColumn(name = "USUARIO", referencedColumnName = "IdEmpleado")
    @ManyToOne
    private ClsEntidadEmpleadoHib usuario;

    public ClsTransferenciaHib() {
    }

    public ClsTransferenciaHib(Integer id) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public ClsEntidadCuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(ClsEntidadCuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public ClsEntidadCuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(ClsEntidadCuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public ClsEntidadEmpleadoHib getUsuario() {
        return usuario;
    }

    public void setUsuario(ClsEntidadEmpleadoHib usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof ClsTransferenciaHib)) {
            return false;
        }
        ClsTransferenciaHib other = (ClsTransferenciaHib) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsTransferenciaHib[ id=" + id + " ]";
    }

    @Override
    public List<ClsEntidadOperacionHib> getOperaciones() {
        ArrayList<ClsEntidadOperacionHib> operaciones = new ArrayList<ClsEntidadOperacionHib>();
        
        ClsEntidadOperacionHib operacionDestino = new ClsEntidadOperacionHib();
        operacionDestino.setTipo(ClsEntidadOperacionHib.TIPO_TRANS_IN);
        operacionDestino.setCuenta(this.cuentaDestino);
        operacionDestino.setCantidad(this.cantidad);
        operacionDestino.setMontoFinal(this.cuentaDestino.getBalance());
        operacionDestino.setMontoInicial(this.cuentaDestino.getBalance().subtract(cantidad));
        operacionDestino.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
        operacionDestino.setHora(new Date());
        
        operaciones.add(operacionDestino);
        
        ClsEntidadOperacionHib operacionOrigen = new ClsEntidadOperacionHib();
        operacionOrigen.setTipo(ClsEntidadOperacionHib.TIPO_TRANS_OUT);
        operacionOrigen.setCuenta(this.cuentaOrigen);
        operacionOrigen.setCantidad(this.cantidad);
        operacionOrigen.setMontoFinal(this.cuentaDestino.getBalance());
        operacionOrigen.setMontoInicial(this.cuentaDestino.getBalance().add(cantidad));
        operacionOrigen.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
        operacionOrigen.setHora(new Date());
        
        operaciones.add(operacionOrigen);
        
        return operaciones;
    }
    
}
