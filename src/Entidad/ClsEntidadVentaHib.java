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
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadVentaHib.findAll", query = "SELECT c FROM ClsEntidadVentaHib c"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByIdVenta", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.idVenta = :idVenta"),
    @NamedQuery(name = "ClsEntidadVentaHib.findBySerie", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.serie = :serie"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByNumero", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.numero = :numero"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByFecha", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByTotalVenta", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.totalVenta = :totalVenta"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByDescuento", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "ClsEntidadVentaHib.findBySubTotal", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.subTotal = :subTotal"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByIgv", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.igv = :igv"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByTotalPagar", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.totalPagar = :totalPagar"),
    @NamedQuery(name = "ClsEntidadVentaHib.findByEstado", query = "SELECT c FROM ClsEntidadVentaHib c WHERE c.estado = :estado")})
public class ClsEntidadVentaHib implements Serializable, IntEntidadTransaccionImprimible, IntOperacion {
    @JoinColumn(name = "cuenta", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuenta;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdVenta")
    private Integer idVenta;
    @Column(name = "Serie")
    private String serie;
    @Column(name = "Numero")
    private String numero;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "TotalVenta")
    private BigDecimal totalVenta;
    @Basic(optional = false)
    @Column(name = "Descuento")
    private BigDecimal descuento;
    @Basic(optional = false)
    @Column(name = "SubTotal")
    private BigDecimal subTotal;
    @Basic(optional = false)
    @Column(name = "Igv")
    private BigDecimal igv;
    @Basic(optional = false)
    @Column(name = "TotalPagar")
    private BigDecimal totalPagar;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @JoinColumn(name = "IdCliente", referencedColumnName = "IdCliente")
    @ManyToOne(optional = false)
    private ClsEntidadClienteHib idCliente;
    @JoinColumn(name = "IdEmpleado", referencedColumnName = "IdEmpleado")
    @ManyToOne(optional = false)
    private ClsEntidadEmpleadoHib idEmpleado;
    @JoinColumn(name = "IdTipoDocumento", referencedColumnName = "IdTipoDocumento")
    @ManyToOne(optional = false)
    private ClsEntidadTipodocumentoHib idTipoDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta", fetch=FetchType.EAGER)
    private Collection<ClsEntidadDetalleventaHib> clsEntidadDetalleventaHibCollection;
    
    public static final String PRO_TIPO_VENTA       = "VENTA";
    public static final String PRO_TIPO_COTIZACION  = "COTIZACION";
    public static final String PRO_TIPO_EMITIDO     = "EMITIDO";

    public ClsEntidadVentaHib() {
    }

    public ClsEntidadVentaHib(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public ClsEntidadVentaHib(Integer idVenta, Date fecha, BigDecimal totalVenta, BigDecimal descuento, BigDecimal subTotal, BigDecimal igv, BigDecimal totalPagar, String estado) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.igv = igv;
        this.totalPagar = totalPagar;
        this.estado = estado;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
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

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ClsEntidadClienteHib getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ClsEntidadClienteHib idCliente) {
        this.idCliente = idCliente;
    }

    public ClsEntidadEmpleadoHib getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(ClsEntidadEmpleadoHib idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ClsEntidadTipodocumentoHib getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(ClsEntidadTipodocumentoHib idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    @XmlTransient
    public Collection<ClsEntidadDetalleventaHib> getClsEntidadDetalleventaHibCollection() {
        return clsEntidadDetalleventaHibCollection;
    }

    public void setClsEntidadDetalleventaHibCollection(Collection<ClsEntidadDetalleventaHib> clsEntidadDetalleventaHibCollection) {
        this.clsEntidadDetalleventaHibCollection = clsEntidadDetalleventaHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadVentaHib)) {
            return false;
        }
        ClsEntidadVentaHib other = (ClsEntidadVentaHib) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadVentaHib[ idVenta=" + idVenta + " ]";
    }

    @Override
    public Integer getId() {
        return this.getIdVenta();
    }

    @Override
    public BigDecimal getTotal() {
        return this.getTotalPagar();
    }

    @Override
    public Collection getClsEntidadDetalleCollection() {
        return this.getClsEntidadDetalleventaHibCollection();
    }

    @Override
    public ClsEntidadProveedorHib getIdProveedor() {
        return null;
    }

    @Override
    public IntTercero getDatosDelTercero() {
        return this.getIdCliente();
    }

    /*
    @Override
    public String getTipoTransaccion() {
        return this.tipoTransaccion;
    }
    
    @Override
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    */

    @Override
    public List<ClsEntidadOperacionHib> getOperaciones(){
        ClsEntidadOperacionHib operacion = new ClsEntidadOperacionHib();
        operacion.setTipo(ClsEntidadOperacionHib.TIPO_VENTA);
        operacion.setCuenta(this.cuenta);
        operacion.setCantidad(this.totalVenta);
        operacion.setMontoFinal(this.cuenta.getBalance());
        operacion.setMontoInicial(this.cuenta.getBalance().subtract(this.getTotal()));
        operacion.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
        operacion.setHora(new Date());
        
        ArrayList operaciones = new ArrayList();
        operaciones.add(operacion);
        
        return operaciones;
    }

    public ClsEntidadCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClsEntidadCuenta cuenta) {
        this.cuenta = cuenta;
    }
    
}
