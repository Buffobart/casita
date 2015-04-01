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
@Table(name = "tipousuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findAll", query = "SELECT c FROM ClsEntidadTipousuarioHib c"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByIdTipoUsuario", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.idTipoUsuario = :idTipoUsuario"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByDescripcion", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPVenta", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pVenta = :pVenta"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPCompra", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pCompra = :pCompra"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPProducto", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pProducto = :pProducto"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPProveedor", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pProveedor = :pProveedor"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPEmpleado", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pEmpleado = :pEmpleado"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPCliente", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pCliente = :pCliente"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPCategoria", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pCategoria = :pCategoria"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPTipodoc", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pTipodoc = :pTipodoc"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPTipouser", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pTipouser = :pTipouser"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPAnularv", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pAnularv = :pAnularv"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPAnularc", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pAnularc = :pAnularc"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPEstadoprod", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pEstadoprod = :pEstadoprod"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPVentare", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pVentare = :pVentare"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPVentade", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pVentade = :pVentade"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPEstadistica", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pEstadistica = :pEstadistica"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPComprare", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pComprare = :pComprare"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPComprade", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pComprade = :pComprade"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPPass", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pPass = :pPass"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPRespaldar", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pRespaldar = :pRespaldar"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPRestaurar", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pRestaurar = :pRestaurar"),
    @NamedQuery(name = "ClsEntidadTipousuarioHib.findByPCaja", query = "SELECT c FROM ClsEntidadTipousuarioHib c WHERE c.pCaja = :pCaja")})
public class ClsEntidadTipousuarioHib implements Serializable {
    @Column(name = "p_cuentas")
    private Integer pCuentas;
    @Column(name = "p_descuento")
    private Integer pDescuento;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdTipoUsuario")
    private Integer idTipoUsuario;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "p_venta")
    private Integer pVenta;
    @Column(name = "p_compra")
    private Integer pCompra;
    @Column(name = "p_producto")
    private Integer pProducto;
    @Column(name = "p_proveedor")
    private Integer pProveedor;
    @Column(name = "p_empleado")
    private Integer pEmpleado;
    @Column(name = "p_cliente")
    private Integer pCliente;
    @Column(name = "p_categoria")
    private Integer pCategoria;
    @Column(name = "p_tipodoc")
    private Integer pTipodoc;
    @Column(name = "p_tipouser")
    private Integer pTipouser;
    @Column(name = "p_anularv")
    private Integer pAnularv;
    @Column(name = "p_anularc")
    private Integer pAnularc;
    @Column(name = "p_estadoprod")
    private Integer pEstadoprod;
    @Column(name = "p_ventare")
    private Integer pVentare;
    @Column(name = "p_ventade")
    private Integer pVentade;
    @Column(name = "p_estadistica")
    private Integer pEstadistica;
    @Column(name = "p_comprare")
    private Integer pComprare;
    @Column(name = "p_comprade")
    private Integer pComprade;
    @Column(name = "p_pass")
    private Integer pPass;
    @Column(name = "p_respaldar")
    private Integer pRespaldar;
    @Column(name = "p_restaurar")
    private Integer pRestaurar;
    @Column(name = "p_caja")
    private Integer pCaja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoUsuario")
    private Collection<ClsEntidadEmpleadoHib> clsEntidadEmpleadoHibCollection;

    public ClsEntidadTipousuarioHib() {
    }

    public ClsEntidadTipousuarioHib(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public ClsEntidadTipousuarioHib(Integer idTipoUsuario, String descripcion) {
        this.idTipoUsuario = idTipoUsuario;
        this.descripcion = descripcion;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPVenta() {
        return pVenta;
    }

    public void setPVenta(Integer pVenta) {
        this.pVenta = pVenta;
    }

    public Integer getPCompra() {
        return pCompra;
    }

    public void setPCompra(Integer pCompra) {
        this.pCompra = pCompra;
    }

    public Integer getPProducto() {
        return pProducto;
    }

    public void setPProducto(Integer pProducto) {
        this.pProducto = pProducto;
    }

    public Integer getPProveedor() {
        return pProveedor;
    }

    public void setPProveedor(Integer pProveedor) {
        this.pProveedor = pProveedor;
    }

    public Integer getPEmpleado() {
        return pEmpleado;
    }

    public void setPEmpleado(Integer pEmpleado) {
        this.pEmpleado = pEmpleado;
    }

    public Integer getPCliente() {
        return pCliente;
    }

    public void setPCliente(Integer pCliente) {
        this.pCliente = pCliente;
    }

    public Integer getPCategoria() {
        return pCategoria;
    }

    public void setPCategoria(Integer pCategoria) {
        this.pCategoria = pCategoria;
    }

    public Integer getPTipodoc() {
        return pTipodoc;
    }

    public void setPTipodoc(Integer pTipodoc) {
        this.pTipodoc = pTipodoc;
    }

    public Integer getPTipouser() {
        return pTipouser;
    }

    public void setPTipouser(Integer pTipouser) {
        this.pTipouser = pTipouser;
    }

    public Integer getPAnularv() {
        return pAnularv;
    }

    public void setPAnularv(Integer pAnularv) {
        this.pAnularv = pAnularv;
    }

    public Integer getPAnularc() {
        return pAnularc;
    }

    public void setPAnularc(Integer pAnularc) {
        this.pAnularc = pAnularc;
    }

    public Integer getPEstadoprod() {
        return pEstadoprod;
    }

    public void setPEstadoprod(Integer pEstadoprod) {
        this.pEstadoprod = pEstadoprod;
    }

    public Integer getPVentare() {
        return pVentare;
    }

    public void setPVentare(Integer pVentare) {
        this.pVentare = pVentare;
    }

    public Integer getPVentade() {
        return pVentade;
    }

    public void setPVentade(Integer pVentade) {
        this.pVentade = pVentade;
    }

    public Integer getPEstadistica() {
        return pEstadistica;
    }

    public void setPEstadistica(Integer pEstadistica) {
        this.pEstadistica = pEstadistica;
    }

    public Integer getPComprare() {
        return pComprare;
    }

    public void setPComprare(Integer pComprare) {
        this.pComprare = pComprare;
    }

    public Integer getPComprade() {
        return pComprade;
    }

    public void setPComprade(Integer pComprade) {
        this.pComprade = pComprade;
    }

    public Integer getPPass() {
        return pPass;
    }

    public void setPPass(Integer pPass) {
        this.pPass = pPass;
    }

    public Integer getPRespaldar() {
        return pRespaldar;
    }

    public void setPRespaldar(Integer pRespaldar) {
        this.pRespaldar = pRespaldar;
    }

    public Integer getPRestaurar() {
        return pRestaurar;
    }

    public void setPRestaurar(Integer pRestaurar) {
        this.pRestaurar = pRestaurar;
    }

    public Integer getPCaja() {
        return pCaja;
    }

    public void setPCaja(Integer pCaja) {
        this.pCaja = pCaja;
    }

    @XmlTransient
    public Collection<ClsEntidadEmpleadoHib> getClsEntidadEmpleadoHibCollection() {
        return clsEntidadEmpleadoHibCollection;
    }

    public void setClsEntidadEmpleadoHibCollection(Collection<ClsEntidadEmpleadoHib> clsEntidadEmpleadoHibCollection) {
        this.clsEntidadEmpleadoHibCollection = clsEntidadEmpleadoHibCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoUsuario != null ? idTipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadTipousuarioHib)) {
            return false;
        }
        ClsEntidadTipousuarioHib other = (ClsEntidadTipousuarioHib) object;
        if ((this.idTipoUsuario == null && other.idTipoUsuario != null) || (this.idTipoUsuario != null && !this.idTipoUsuario.equals(other.idTipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadTipousuarioHib[ idTipoUsuario=" + idTipoUsuario + " ]";
    }

    public Integer getPCuentas() {
        return pCuentas;
    }

    public void setPCuentas(Integer pCuentas) {
        this.pCuentas = pCuentas;
    }

    public Integer getPDescuento() {
        return pDescuento;
    }

    public void setPDescuento(Integer pDescuento) {
        this.pDescuento = pDescuento;
    }
    
}
