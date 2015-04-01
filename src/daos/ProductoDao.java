/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadProductoHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface ProductoDao {
    List<ClsEntidadProductoHib> getAllProductos(boolean includeInactive);
    List<ClsEntidadProductoHib> getProductosPorNombre(String nombre, boolean includeInactive);
    List<ClsEntidadProductoHib> getProductosPorCodigo(String codigo, boolean includeInactive);
    List<ClsEntidadProductoHib> getProductosPorDescripcion(String descripcion, boolean includeInactive);
    void borrarProducto(ClsEntidadProductoHib borrar);
    void desactivarProducto(ClsEntidadProductoHib producto);
}
