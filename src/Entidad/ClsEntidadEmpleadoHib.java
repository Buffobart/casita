/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findAll", query = "SELECT c FROM ClsEntidadEmpleadoHib c"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByIdEmpleado", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByNombre", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByApellido", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findBySexo", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.sexo = :sexo"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByFechaNac", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.fechaNac = :fechaNac"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByDireccion", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByTelefono", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByCelular", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.celular = :celular"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByEmail", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.email = :email"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByDni", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.dni = :dni"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByFechaIng", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.fechaIng = :fechaIng"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findBySueldo", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.sueldo = :sueldo"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByEstado", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.estado = :estado"),
    @NamedQuery(name = "ClsEntidadEmpleadoHib.findByUsuario", query = "SELECT c FROM ClsEntidadEmpleadoHib c WHERE c.usuario = :usuario")})
public class ClsEntidadEmpleadoHib implements Serializable {
    @JoinColumn(name = "cuenta", referencedColumnName = "IdCuenta")
    @ManyToOne
    private ClsEntidadCuenta cuenta;
    @OneToMany(mappedBy = "usuario")
    private Collection<ClsEntidadOperacionHib> clsEntidadOperacionHibCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdEmpleado")
    private Integer idEmpleado;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "Sexo")
    private String sexo;
    @Basic(optional = false)
    @Column(name = "FechaNac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "Celular")
    private String celular;
    @Column(name = "Email")
    private String email;
    @Column(name = "Dni")
    private String dni;
    @Basic(optional = false)
    @Column(name = "FechaIng")
    @Temporal(TemporalType.DATE)
    private Date fechaIng;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Sueldo")
    private BigDecimal sueldo;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @Column(name = "Usuario")
    private String usuario;
    @Lob
    @Column(name = "Contrasena")
    private String contrasena;
    @JoinColumn(name = "IdTipoUsuario", referencedColumnName = "IdTipoUsuario")
    @ManyToOne(optional = false)
    private ClsEntidadTipousuarioHib idTipoUsuario;

    public ClsEntidadEmpleadoHib() {
    }

    public ClsEntidadEmpleadoHib(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ClsEntidadEmpleadoHib(Integer idEmpleado, String nombre, String apellido, String sexo, Date fechaNac, Date fechaIng, String estado) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.fechaIng = fechaIng;
        this.estado = estado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaIng() {
        return fechaIng;
    }

    public void setFechaIng(Date fechaIng) {
        this.fechaIng = fechaIng;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public ClsEntidadTipousuarioHib getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(ClsEntidadTipousuarioHib idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsEntidadEmpleadoHib)) {
            return false;
        }
        ClsEntidadEmpleadoHib other = (ClsEntidadEmpleadoHib) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ClsEntidadEmpleadoHib[ idEmpleado=" + idEmpleado + " ]";
    }

    @XmlTransient
    public Collection<ClsEntidadVentaHib> getClsEntidadVentaHibCollection() {
        return clsEntidadVentaHibCollection;
    }

    public void setClsEntidadVentaHibCollection(Collection<ClsEntidadVentaHib> clsEntidadVentaHibCollection) {
        this.clsEntidadVentaHibCollection = clsEntidadVentaHibCollection;
    }

    @XmlTransient
    public Collection<ClsEntidadCompraHib> getClsEntidadCompraHibCollection() {
        return clsEntidadCompraHibCollection;
    }

    public void setClsEntidadCompraHibCollection(Collection<ClsEntidadCompraHib> clsEntidadCompraHibCollection) {
        this.clsEntidadCompraHibCollection = clsEntidadCompraHibCollection;
    }

    @XmlTransient
    public Collection<ClsEntidadOperacionHib> getClsEntidadOperacionHibCollection() {
        return clsEntidadOperacionHibCollection;
    }

    public void setClsEntidadOperacionHibCollection(Collection<ClsEntidadOperacionHib> clsEntidadOperacionHibCollection) {
        this.clsEntidadOperacionHibCollection = clsEntidadOperacionHibCollection;
    }

    public ClsEntidadCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClsEntidadCuenta cuenta) {
        this.cuenta = cuenta;
    }
    
}
