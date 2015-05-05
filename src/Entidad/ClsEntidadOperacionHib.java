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
@Table(name = "operacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadOperacionHib.findAll", query = "SELECT c FROM ClsEntidadOperacionHib c"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findById", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.id = :id"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findByTipo", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findByCantidad", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findByMontoInicial", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.montoInicial = :montoInicial"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findByMontoFinal", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.montoFinal = :montoFinal"),
    @NamedQuery(name = "ClsEntidadOperacionHib.findByHora", query = "SELECT c FROM ClsEntidadOperacionHib c WHERE c.hora = :hora")})
public class ClsEntidadOperacionHib implements Serializable {
    
    public static final String TIPO_VENTA = "VENTA";
    public static final String TIPO_ACTUALIZCION = "ACTUALIZACION";
    public static final String TIPO_COMPRA = "COMPRA";
    public static final String TIPO_TRANS_OUT = "TRANSFERENCIA RETIRO";
    public static final String TIPO_TRANS_IN = "TRANSFERENCIA DEPOSITO";
    public static final String TIPO_GASTOS_VARIOS = "GASTOS VARIOS";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "montoInicial")
    private BigDecimal montoInicial;
    @Column(name = "montoFinal")
    private BigDecimal montoFinal;
    @Column(name = "hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @JoinColumn(name = "cuenta", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuenta;
    @JoinColumn(name = "usuario", referencedColumnName = "IdEmpleado")
    @ManyToOne
    private ClsEntidadEmpleadoHib usuario;

    public ClsEntidadOperacionHib() {
    }

    public ClsEntidadOperacionHib(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public ClsEntidadCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClsEntidadCuenta cuenta) {
        this.cuenta = cuenta;
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
        if (!(object instanceof ClsEntidadOperacionHib)) {
            return false;
        }
        ClsEntidadOperacionHib other = (ClsEntidadOperacionHib) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadOperacionHib[ id=" + id + " ]";
    }
    
}
