/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadProductoHib.findAll", query = "SELECT c FROM ClsEntidadProductoHib c"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByIdProducto", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.idProducto = :idProducto"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByCodigo", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByNombre", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByStock", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.stock = :stock"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByStockMin", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.stockMin = :stockMin"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByPrecioCosto", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.precioCosto = :precioCosto"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByPrecioVenta", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.precioVenta = :precioVenta"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByPrecioVenta2", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.precioVenta2 = :precioVenta2"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByUtilidad", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.utilidad = :utilidad"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByEstado", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.estado = :estado"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByDescuentoProveedor", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.descuentoProveedor = :descuentoProveedor"),
    @NamedQuery(name = "ClsEntidadProductoHib.findByDescuentoVenta", query = "SELECT c FROM ClsEntidadProductoHib c WHERE c.descuentoVenta = :descuentoVenta")})
public class ClsEntidadProductoHib implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProducto")
    private Integer idProducto;
    @Column(name = "Codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Stock")
    private BigDecimal stock;
    @Column(name = "StockMin")
    private BigDecimal stockMin;
    @Column(name = "PrecioCosto")
    private BigDecimal precioCosto;
    @Column(name = "PrecioVenta")
    private BigDecimal precioVenta;
    @Column(name = "PrecioVenta2")
    private BigDecimal precioVenta2;
    @Column(name = "Utilidad")
    private BigDecimal utilidad;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "DescuentoProveedor")
    private float descuentoProveedor;
    @Basic(optional = false)
    @Column(name = "DescuentoVenta")
    private float descuentoVenta;
    @JoinColumn(name = "Moneda", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClsEntidadMonedaHib moneda;
    @JoinColumn(name = "IdCategoria", referencedColumnName = "IdCategoria")
    @ManyToOne(optional = false)
    private ClsEntidadCategoriaHib idCategoria;
    @JoinColumn(name = "Proveedor", referencedColumnName = "IdProveedor")
    @ManyToOne
    private ClsEntidadProveedorHib proveedor;
    
    public static final String PROP_INACTIVO = "INACTIVO";
    public static final String PROP_ACTIVO = "ACTIVO";

    public ClsEntidadProductoHib() {
    }

    public ClsEntidadProductoHib(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public ClsEntidadProductoHib(Integer idProducto, String nombre, String estado, float descuentoProveedor, float descuentoVenta) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.estado = estado;
        this.descuentoProveedor = descuentoProveedor;
        this.descuentoVenta = descuentoVenta;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioVenta2() {
        return precioVenta2;
    }

    public void setPrecioVenta2(BigDecimal precioVenta2) {
        this.precioVenta2 = precioVenta2;
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getDescuentoProveedor() {
        return descuentoProveedor;
    }

    public void setDescuentoProveedor(float descuentoProveedor) {
        this.descuentoProveedor = descuentoProveedor;
    }

    public float getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(float descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public ClsEntidadMonedaHib getMoneda() {
        return moneda;
    }

    public void setMoneda(ClsEntidadMonedaHib moneda) {
        this.moneda = moneda;
    }

    public ClsEntidadCategoriaHib getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(ClsEntidadCategoriaHib idCategoria) {
        this.idCategoria = idCategoria;
    }

    public ClsEntidadProveedorHib getProveedor() {
        return proveedor;
    }

    public void setProveedor(ClsEntidadProveedorHib proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadProductoHib)) {
            return false;
        }
        ClsEntidadProductoHib other = (ClsEntidadProductoHib) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadProductoHib[ idProducto=" + idProducto + " ]";
    }
    
}
