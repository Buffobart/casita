/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsGastosVariosHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface GastosVariosDao {
    
    public List<ClsGastosVariosHib> getAllGastos();
    public void addGasto(ClsGastosVariosHib gasto);
    
}
