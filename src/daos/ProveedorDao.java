/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadProveedorContactoHib;
import Entidad.ClsEntidadProveedorHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface ProveedorDao {
    public List<ClsEntidadProveedorHib> getAllProveedores();
    
    public ClsEntidadProveedorHib getProveedorById(int id);
    
    public List<ClsEntidadProveedorHib> buscaPorId( String id );

    public List<ClsEntidadProveedorHib> buscaPorNombre( String nombre );

    public List<ClsEntidadProveedorHib> buscaPorRfc( String rfc );

    public List<ClsEntidadProveedorHib> buscaPorDni( String dni );
    
    public void agregarOActualizaProveedor(ClsEntidadProveedorHib proveedor);
    
    public void borrarContactoProveedor(ClsEntidadProveedorContactoHib contacto);
    
    public void agregarOActualizaContactoProveedor(ClsEntidadProveedorContactoHib contacto);
}
