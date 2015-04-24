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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadCompraHib.findAll", query = "SELECT c FROM ClsEntidadCompraHib c"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByIdCompra", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.idCompra = :idCompra"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByNumero", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.numero = :numero"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByFecha", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ClsEntidadCompraHib.findBySubTotal", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.subTotal = :subTotal"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByIgv", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.igv = :igv"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByTotal", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.total = :total"),
    @NamedQuery(name = "ClsEntidadCompraHib.findByEstado", query = "SELECT c FROM ClsEntidadCompraHib c WHERE c.estado = :estado")})
public class ClsEntidadCompraHib implements Serializable, IntEntidadTransaccionImprimible, IntOperacion {
    @JoinColumn(name = "cuenta", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra", fetch=FetchType.EAGER)
    private Collection<ClsEntidadDetallecompraHib> clsEntidadDetallecompraHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCompra")
    private Integer idCompra;
    @Column(name = "Numero")
    private String numero;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SubTotal")
    private BigDecimal subTotal;
    @Column(name = "Igv")
    private BigDecimal igv;
    @Column(name = "Total")
    private BigDecimal total;
    @Column(name = "Estado")
    private String estado;
    @JoinColumn(name = "IdEmpleado", referencedColumnName = "IdEmpleado")
    @ManyToOne(optional = false)
    private ClsEntidadEmpleadoHib idEmpleado;
    @JoinColumn(name = "IdProveedor", referencedColumnName = "IdProveedor")
    @ManyToOne(optional = false)
    private ClsEntidadProveedorHib idProveedor;
    @JoinColumn(name = "IdTipoDocumento", referencedColumnName = "IdTipoDocumento")
    @ManyToOne(optional = false)
    private ClsEntidadTipodocumentoHib idTipoDocumento;
    
    public static final String PRO_COMPRA = "COMPRA";

    public ClsEntidadCompraHib() {
    }

    public ClsEntidadCompraHib(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ClsEntidadEmpleadoHib getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(ClsEntidadEmpleadoHib idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ClsEntidadProveedorHib getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ClsEntidadProveedorHib idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ClsEntidadTipodocumentoHib getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(ClsEntidadTipodocumentoHib idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadCompraHib)) {
            return false;
        }
        ClsEntidadCompraHib other = (ClsEntidadCompraHib) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadCompraHib[ idCompra=" + idCompra + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadDetallecompraHib> getClsEntidadDetallecompraHibCollection() {
        return clsEntidadDetallecompraHibCollection;
    }

    public void setClsEntidadDetallecompraHibCollection(Collection<ClsEntidadDetallecompraHib> clsEntidadDetallecompraHibCollection) {
        this.clsEntidadDetallecompraHibCollection = clsEntidadDetallecompraHibCollection;
    }

    @Override
    public Integer getId() {
        return this.getIdCompra();
    }

    @Override
    public String getSerie() {
        return this.getNumero();
    }

    @Override
    public BigDecimal getTotalPagar() {
        return this.getTotal();
    }

    @Override
    public Collection getClsEntidadDetalleCollection() {
        //return this.getClsEntidadDetallecompraHibCollection();
        return this.getClsEntidadDetallecompraHibCollection();
    }

    @Override
    public IntTercero getDatosDelTercero() {
        return this.getIdProveedor();
    }

    /*
    @Override
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    @Override
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    */

    public ClsEntidadCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClsEntidadCuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public List<ClsEntidadOperacionHib> getOperaciones(){
        ClsEntidadOperacionHib operacion = new ClsEntidadOperacionHib();
        operacion.setTipo(ClsEntidadOperacionHib.TIPO_COMPRA);
        operacion.setCuenta(this.cuenta);
        operacion.setCantidad(this.getTotal());
        operacion.setMontoFinal(this.cuenta.getBalance());
        operacion.setMontoInicial(this.cuenta.getBalance().add(this.getTotal()));
        operacion.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
        operacion.setHora(new Date());
        
        ArrayList operaciones = new ArrayList();
        operaciones.add(operacion);
        
        return operaciones;
    }
    
}
