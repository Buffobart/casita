/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadVentaHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface VentaDao {

    /**
     *
     * @param id
     */
    
    public List<ClsEntidadVentaHib> getAllVentas();
    public List<ClsEntidadVentaHib> getAllCotizaciones();
    public void generarOrdenDeCompra(int id);
    public void saveVenta(ClsEntidadVentaHib venta);
}
